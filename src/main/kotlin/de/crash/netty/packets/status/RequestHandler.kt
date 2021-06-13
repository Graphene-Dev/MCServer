package de.crash.netty.packets.status

import de.crash.netty.packets.Packet
import de.crash.netty.packets.PacketHandler
import io.netty.channel.Channel

class RequestHandler : PacketHandler {
    override fun handle(channel: Channel, packet: Packet) {
        ResponsePacket().sendPacket(channel)
    }
}