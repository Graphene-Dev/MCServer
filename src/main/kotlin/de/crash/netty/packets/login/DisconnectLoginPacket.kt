package de.crash.netty.packets.login

import de.crash.netty.packets.Packet
import de.crash.netty.packets.PacketType
import de.crash.netty.packets.SendPacket
import de.crash.netty.packets.sendPacket
import io.netty.channel.Channel

class DisconnectLoginPacket : SendPacket {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(PacketType.DISCONNECT_LOGIN)
        channel.sendPacket(packet)
    }
}