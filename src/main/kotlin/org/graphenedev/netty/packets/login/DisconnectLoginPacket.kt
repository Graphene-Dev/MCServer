package org.graphenedev.netty.packets.login

import io.netty.channel.Channel
import org.graphenedev.netty.packets.Packet
import org.graphenedev.netty.packets.PacketType
import org.graphenedev.netty.packets.SendPacket
import org.graphenedev.netty.packets.sendPacket

class DisconnectLoginPacket : SendPacket {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(PacketType.DISCONNECT_LOGIN)
        // ADD Chat Component
        channel.sendPacket(packet)
    }
}