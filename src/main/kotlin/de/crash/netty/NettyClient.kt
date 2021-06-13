package de.crash.netty

import de.crash.netty.packets.Packet
import de.crash.netty.packets.sendPacket
import io.netty.channel.Channel

class NettyClient(val channel: Channel, val channelId: String, var state: ClientStatus) {
    fun sendPacket(packet: Packet) = channel.sendPacket(packet)
}