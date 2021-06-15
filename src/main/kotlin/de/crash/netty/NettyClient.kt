package de.crash.netty

import de.crash.netty.packets.Packet
import de.crash.netty.packets.sendPacket
import io.netty.channel.Channel

class NettyClient(val channel: Channel, val channelId: String, var state: ClientStatus) {
    var protocolVersion: Int = 0
    fun sendPacket(packet: Packet) = channel.sendPacket(packet)
}