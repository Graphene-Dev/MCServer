package de.crash.netty.packets.handshake

import de.crash.netty.packets.Packet
import de.crash.netty.packets.PacketHandler
import io.netty.channel.Channel

class HandshakeHandler : PacketHandler {
    override fun handle(channel: Channel, packet: Packet) {
        println("Handshake")
    }
}