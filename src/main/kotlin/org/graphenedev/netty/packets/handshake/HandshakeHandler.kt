package org.graphenedev.netty.packets.handshake

import io.netty.channel.Channel
import org.graphenedev.netty.getStateById
import org.graphenedev.netty.packets.HandlePacket
import org.graphenedev.netty.packets.Packet
import org.graphenedev.netty.packets.nettyClients

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