package org.graphenedev.netty

import io.netty.channel.Channel
import org.graphenedev.netty.packets.Packet
import org.graphenedev.netty.packets.sendPacket

class NettyClient(val channel: Channel, val channelId: String, var state: ClientStatus) {
    var protocolVersion: Int = 0
    fun sendPacket(packet: Packet) = channel.sendPacket(packet)
}