package de.crash.netty.packets.status

import de.crash.netty.packets.HandlePacket
import de.crash.netty.packets.Packet
import io.netty.channel.Channel

class RequestHandler : HandlePacket {
    override fun handle(channel: Channel, packet: Packet) {
        ResponsePacket().sendPacket(channel)
    }
}