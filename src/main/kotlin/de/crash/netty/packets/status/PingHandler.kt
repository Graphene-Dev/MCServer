package de.crash.netty.packets.status

import de.crash.netty.packets.Packet
import de.crash.netty.packets.PacketHandler
import io.netty.channel.Channel

class PingHandler : PacketHandler {
    override fun handle(channel: Channel, packet: Packet) {
        val payload = packet.readVarLong()
        println("Payload Received: $payload")
        PongPacket(payload).sendPacket(channel)
    }
}