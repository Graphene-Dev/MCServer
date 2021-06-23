package org.graphenedev.netty.packets.play

import io.netty.channel.Channel
import org.graphenedev.netty.getStateById
import org.graphenedev.netty.packets.PacketHandler
import org.graphenedev.netty.packets.Packet
import org.graphenedev.netty.packets.nettyClients

class ChatMessageServerboundPackets() : PacketHandler {
    override fun handle(channel: Channel, packet: Packet) {
        val message = packet.readString()
    }
}