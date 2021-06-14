package de.crash.netty.packets.status

import de.crash.netty.packets.HandlePacket
import de.crash.netty.packets.Packet
import io.netty.channel.Channel

class PingStatusHandler : HandlePacket {
    override fun handle(channel: Channel, packet: Packet) {
        val payload = packet.readVarLong()
        println("Payload Received: $payload")
        PongStatusPacket(payload).sendPacket(channel)
    }
}