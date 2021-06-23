package org.graphenedev.netty.packets.status

import io.netty.channel.Channel
import org.graphenedev.netty.packets.PacketHandler
import org.graphenedev.netty.packets.Packet

class RequestHandler : PacketHandler {
    override fun handle(channel: Channel, packet: Packet) {
        ResponsePacket().sendPacket(channel)
    }
}