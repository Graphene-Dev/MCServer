package de.crash.netty.packets.handshake

import de.crash.netty.getStateById
import de.crash.netty.packets.HandlePacket
import de.crash.netty.packets.Packet
import de.crash.netty.packets.nettyClients
import io.netty.channel.Channel

class HandshakeHandler : HandlePacket {
    override fun handle(channel: Channel, packet: Packet) {
        val protocolVersion = packet.readVarInt()
        packet.readString() // Server IP
        packet.readShort() // Port
        val newState = packet.readVarInt()
        val nettyClient = nettyClients[channel]!!
        nettyClient.state = getStateById(newState)
        nettyClient.protocolVersion = protocolVersion
    }
}