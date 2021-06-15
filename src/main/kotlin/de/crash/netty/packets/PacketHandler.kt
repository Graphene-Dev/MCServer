package de.crash.netty.packets

import de.crash.netty.ClientStatus
import de.crash.netty.NettyClient
import de.crash.netty.packets.handshake.HandshakeHandler
import de.crash.netty.packets.login.LoginStartHandler
import de.crash.netty.packets.status.PingStatusHandler
import de.crash.netty.packets.status.RequestHandler
import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext

internal interface PacketHandler
internal interface HandlePacket: PacketHandler {
    fun handle(channel: Channel, packet: Packet)
}
internal interface SendPacket: PacketHandler {
    fun sendPacket(channel: Channel)
}

internal val packetHandlers = hashMapOf<ClientStatus, HashMap<Int, HandlePacket>>()
internal val nettyClients = hashMapOf<Channel, NettyClient>()

internal fun initPacketHandlers(){
    val handShakePacketMap = HashMap<Int, HandlePacket>()
    handShakePacketMap[PacketType.HANDSHAKE.id] = HandshakeHandler()
    packetHandlers[ClientStatus.HANDSHAKE] = handShakePacketMap
    val statusPacketMap = HashMap<Int, HandlePacket>()
    statusPacketMap[PacketType.REQUEST.id] = RequestHandler()
    statusPacketMap[PacketType.PING_STATUS.id] = PingStatusHandler()
    packetHandlers[ClientStatus.STATUS] = statusPacketMap
    val loginPacketMap = HashMap<Int, HandlePacket>()
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