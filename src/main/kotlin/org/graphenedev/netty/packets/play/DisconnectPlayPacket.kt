package org.graphenedev.netty.packets.play

import io.netty.channel.Channel
import org.graphenedev.netty.packets.Packet
import org.graphenedev.netty.packets.PacketType
import org.graphenedev.netty.packets.PacketSender
import org.graphenedev.netty.packets.sendPacket

class DisconnectPlayPacket : PacketSender {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(PacketType.DISCONNECT_PLAY)
        // ADD Chat Component
        channel.sendPacket(packet)
    }
}