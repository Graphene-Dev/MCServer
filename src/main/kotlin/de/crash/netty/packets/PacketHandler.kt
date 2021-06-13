package de.crash.netty.packets

import de.crash.netty.packets.handshake.HandshakeHandler
import de.crash.netty.packets.status.PingHandler
import de.crash.netty.packets.status.RequestHandler
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
    val statusPacketMap = HashMap<Int, PacketHandler>()
    statusPacketMap[0] = RequestHandler()
    statusPacketMap[1] = PingHandler()
    packetHandlers[1] = statusPacketMap
}

fun ChannelHandlerContext.handle(bytes: ByteArray) {
    val packet = Packet(bytes)
    while (packet.bytes.size > packet.readPos){
        val length = packet.readVarInt()
        val packedId = packet.readVarInt()
        println(packedId)
        val status = statusMap[channel().id().asLongText()] ?: ClientStatus.HANDSHAKE.ordinal
        try {
            packetHandlers[status]!![packedId]!!.handle(channel(), packet)
        }catch (ex: NullPointerException) {
            println("Unhandled Packet with id $packedId, status $status:")
            ex.printStackTrace()
            break
        }
    }
}