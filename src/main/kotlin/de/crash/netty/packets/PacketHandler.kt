package de.crash.netty.packets

import de.crash.netty.ClientStatus
import de.crash.netty.NettyClient
import de.crash.netty.packets.handshake.HandshakeHandler
import de.crash.netty.packets.login.LoginStartHandler
import de.crash.netty.packets.status.PingHandler
import de.crash.netty.packets.status.RequestHandler
import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext

interface PacketHandler {
    fun handle(channel: Channel, packet: Packet)
}

val packetHandlers = hashMapOf<ClientStatus, HashMap<Int, PacketHandler>>()
val nettyClients = hashMapOf<Channel, NettyClient>()

fun initPacketHandlers(){
    val handShakePacketMap = HashMap<Int, PacketHandler>()
    handShakePacketMap[0] = HandshakeHandler()
    packetHandlers[ClientStatus.HANDSHAKE] = handShakePacketMap
    val statusPacketMap = HashMap<Int, PacketHandler>()
    statusPacketMap[0] = RequestHandler()
    statusPacketMap[1] = PingHandler()
    packetHandlers[ClientStatus.STATUS] = statusPacketMap
    val loginPacketMap = HashMap<Int, PacketHandler>()
    loginPacketMap[0] = LoginStartHandler()
    packetHandlers[ClientStatus.LOGIN] = loginPacketMap
}

fun ChannelHandlerContext.handle(bytes: ByteArray) {
    val packet = Packet(bytes)
    while (packet.bytes.size > packet.readPos){
        val length = packet.readVarInt()
        val packedId = packet.readVarInt()
        val state = nettyClients[channel()]?.state ?: ClientStatus.HANDSHAKE
        try {
            packetHandlers[state]!![packedId]!!.handle(channel(), packet)
        }catch (ex: NullPointerException) {
            println("Unhandled Packet with id $packedId, status ${state.name}:")
            ex.printStackTrace()
            break
        }
    }
}

fun Channel.sendPacket(packet: Packet) {
    val cf = this.writeAndFlush(Unpooled.copiedBuffer(packet.getPacketBytes()))
    cf.addListener(ChannelFutureListener.CLOSE)
}