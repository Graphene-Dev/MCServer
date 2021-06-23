package org.graphenedev.netty.packets.status

import io.netty.channel.Channel
import org.graphenedev.netty.packets.PacketHandler
import org.graphenedev.netty.packets.Packet

class PingStatusHandler : PacketHandler {
    override fun handle(channel: Channel, packet: Packet) {
        val payload = packet.readVarLong()
        println("Payload Received: $payload")
        PongStatusPacket(payload).sendPacket(channel)
    }
}