package de.crash.netty.packets.play

import de.crash.netty.packets.Packet
import de.crash.netty.packets.PacketType
import de.crash.netty.packets.SendPacket
import de.crash.netty.packets.sendPacket
import io.netty.channel.Channel

class DisconnectPlayPacket : SendPacket {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(PacketType.DISCONNECT_PLAY)
        // ADD Chat Component
        channel.sendPacket(packet)
    }
}