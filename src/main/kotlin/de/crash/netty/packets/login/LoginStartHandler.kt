package de.crash.netty.packets.login

import de.crash.mojangapi.getUUIDofUsername
import de.crash.netty.packets.HandlePacket
import de.crash.netty.packets.Packet
import io.netty.channel.Channel

class LoginStartHandler : HandlePacket {
    override fun handle(channel: Channel, packet: Packet) {
        val username = packet.readString()
        LoginSuccessPacket(username, getUUIDofUsername(username)).sendPacket(channel)
    }
}