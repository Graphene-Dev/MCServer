package de.crash.netty.packets.play

import de.crash.netty.packets.Packet
import de.crash.netty.packets.PacketHandler
import io.netty.channel.Channel

class LoginStartHandler : PacketHandler {
    override fun handle(channel: Channel, packet: Packet) {
        val username = packet.readString()
        println(username)
    }
}