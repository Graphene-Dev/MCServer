package de.crash.netty.packets.status

import de.crash.netty.packets.Packet
import de.crash.netty.packets.sendPacket
import io.netty.channel.Channel

class PongPacket(val payload: Long) {
    fun sendPacket(channel: Channel) {
        val packet = Packet(1)
        packet.write(payload)
        channel.sendPacket(packet)
    }
}