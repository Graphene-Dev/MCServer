package org.graphenedev.netty.packets.login

import io.netty.channel.Channel
import org.graphenedev.netty.packets.Packet
import org.graphenedev.netty.packets.PacketType
import org.graphenedev.netty.packets.PacketSender
import org.graphenedev.netty.packets.sendPacket

class DisconnectLoginPacket : PacketSender {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(PacketType.DISCONNECT_LOGIN)
        // ADD Chat Component
        channel.sendPacket(packet)
    }
}