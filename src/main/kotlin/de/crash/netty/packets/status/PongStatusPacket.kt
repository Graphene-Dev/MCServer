package de.crash.netty.packets.status

import de.crash.netty.packets.Packet
import de.crash.netty.packets.PacketType
import de.crash.netty.packets.SendPacket
import de.crash.netty.packets.sendPacket
import io.netty.channel.Channel

class PongStatusPacket(private val payload: Long) : SendPacket {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(PacketType.PONG_STATUS)
        packet.writeAsVarLong(payload)
        channel.sendPacket(packet)
    }
}