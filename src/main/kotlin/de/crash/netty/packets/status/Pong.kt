package de.crash.netty.packets.status

import de.crash.netty.packets.Packet
import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import io.netty.channel.ChannelFutureListener

class Pong(val payload: Long) {
    fun sendPacket(channel: Channel) {
        val packet = Packet(1)
        packet.write(payload)
        val cf = channel.writeAndFlush(Unpooled.copiedBuffer(packet.getPacketBytes()))
        cf.addListener(ChannelFutureListener.CLOSE)
    }
}