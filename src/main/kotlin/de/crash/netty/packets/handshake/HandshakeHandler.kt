package de.crash.netty.packets.handshake

import de.crash.netty.packets.Packet
import de.crash.netty.packets.PacketHandler
import de.crash.netty.packets.statusMap
import io.netty.channel.Channel

class HandshakeHandler : PacketHandler {
    override fun handle(channel: Channel, packet: Packet) {
        val protocolVersion = packet.readVarInt()
        val serverAddress = packet.readString()
        val port = packet.readShort()
        val newState = packet.readVarInt()
        statusMap[channel.id().asLongText()] = newState
    }
}