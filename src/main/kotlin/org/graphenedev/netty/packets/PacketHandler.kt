package org.graphenedev.netty.packets

import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import org.graphenedev.netty.ClientStatus
import org.graphenedev.netty.NettyClient
import org.graphenedev.netty.packets.handshake.HandshakeHandler
import org.graphenedev.netty.packets.login.LoginStartHandler
import org.graphenedev.netty.packets.status.PingStatusHandler
import org.graphenedev.netty.packets.status.RequestHandler

internal interface PacketHandler {
    fun handle(channel: Channel, packet: Packet)
}
internal interface PacketSender {
    fun sendPacket(channel: Channel)
}

internal val packetHandlers = hashMapOf<ClientStatus, HashMap<Int, PacketHandler>>()
internal val nettyClients = hashMapOf<Channel, NettyClient>()

internal fun initPacketHandlers(){
    val handShakePacketMap = HashMap<Int, PacketHandler>()
    handShakePacketMap[PacketType.HANDSHAKE.id] = HandshakeHandler()
    packetHandlers[ClientStatus.HANDSHAKE] = handShakePacketMap
    val statusPacketMap = HashMap<Int, PacketHandler>()
    statusPacketMap[PacketType.REQUEST.id] = RequestHandler()
    statusPacketMap[PacketType.PING_STATUS.id] = PingStatusHandler()
    packetHandlers[ClientStatus.STATUS] = statusPacketMap
    val loginPacketMap = HashMap<Int, PacketHandler>()
    loginPacketMap[PacketType.LOGIN_START.id] = LoginStartHandler()
    packetHandlers[ClientStatus.LOGIN] = loginPacketMap
}

internal fun ChannelHandlerContext.handle(bytes: ByteArray) {
    val packet = Packet(bytes)
    while (packet.bytes.size > packet.readPos){
        packet.readVarInt()
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