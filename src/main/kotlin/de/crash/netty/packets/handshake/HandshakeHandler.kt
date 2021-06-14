package de.crash.netty.packets.handshake

import de.crash.netty.getStateById
import de.crash.netty.packets.HandlePacket
import de.crash.netty.packets.Packet
import de.crash.netty.packets.nettyClients
import io.netty.channel.Channel

class HandshakeHandler : HandlePacket {
    override fun handle(channel: Channel, packet: Packet) {
        val protocolVersion = packet.readVarInt()
        val serverAddress = packet.readString()
        val port = packet.readShort()
        val newState = packet.readVarInt()
        nettyClients[channel]!!.state = getStateById(newState)
    }
}