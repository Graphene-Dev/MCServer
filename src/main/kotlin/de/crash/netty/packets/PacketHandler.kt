package de.crash.netty.packets

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

val packetHandlers = hashMapOf<Int, HashMap<Int, PacketHandler>>()
val statusMap = hashMapOf<String, Int>()

fun initPacketHandlers(){
    val handShakePacketMap = HashMap<Int, PacketHandler>()
    handShakePacketMap[0] = HandshakeHandler()
    packetHandlers[0] = handShakePacketMap
    val statusPacketMap = HashMap<Int, PacketHandler>()
    statusPacketMap[0] = RequestHandler()
    statusPacketMap[1] = PingHandler()
    packetHandlers[1] = statusPacketMap
    val playPacketMap = HashMap<Int, PacketHandler>()
    playPacketMap[0] = LoginStartHandler()
    packetHandlers[2] = playPacketMap
}

fun ChannelHandlerContext.handle(bytes: ByteArray) {
    val packet = Packet(bytes)
    while (packet.bytes.size > packet.readPos){
        val length = packet.readVarInt()
        val packedId = packet.readVarInt()
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

fun Channel.sendPacket(packet: Packet) {
    val cf = this.writeAndFlush(Unpooled.copiedBuffer(packet.getPacketBytes()))
    cf.addListener(ChannelFutureListener.CLOSE)
}