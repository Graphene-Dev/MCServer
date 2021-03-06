package org.graphenedev.netty.packets.status

import io.netty.channel.Channel
import org.graphenedev.netty.packets.Packet
import org.graphenedev.netty.packets.PacketType
import org.graphenedev.netty.packets.PacketSender
import org.graphenedev.netty.packets.sendPacket

class PongStatusPacket(private val payload: Long) : PacketSender {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(PacketType.PONG_STATUS)
        packet.writeAsVarLong(payload)
        channel.sendPacket(packet)
    }
}