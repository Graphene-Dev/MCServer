package de.crash.netty.packets

import de.crash.netty.packets.handshake.HandshakeHandler
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext

interface PacketHandler {
    fun handle(channel: Channel, packet: Packet)
}

val packetHandlers = hashMapOf<Int, HashMap<Int, PacketHandler>>()
val statusMap = hashMapOf<String, Int>()

fun initPacketHandlers(){
    val handShakeMap = HashMap<Int, PacketHandler>()
    handShakeMap[0] = HandshakeHandler()
    packetHandlers[0] = handShakeMap
}

fun ChannelHandlerContext.handle(bytes: ByteArray) {
    val packet = Packet(bytes)
    val length = packet.readInt()
    val packedId = packet.readInt()
    val status = statusMap[channel().id().asLongText()] ?: ClientStatus.HANDSHAKE.ordinal
    packetHandlers[status]!![packedId]!!.handle(channel(), packet)
}