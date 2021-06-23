package org.graphenedev.netty.packets.play

import io.netty.channel.Channel
import org.graphenedev.netty.packets.Packet
import org.graphenedev.netty.packets.PacketType
import org.graphenedev.netty.packets.PacketSender
import org.graphenedev.netty.packets.sendPacket

class ClearTitlesPacket(private val reset: Boolean = false) : PacketSender {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(PacketType.CLEAR_TITLES)
        packet.write(reset)
        channel.sendPacket(packet)
    }
}