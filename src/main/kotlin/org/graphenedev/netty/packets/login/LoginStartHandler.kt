package org.graphenedev.netty.packets.login

import io.netty.channel.Channel
import org.graphenedev.mojangapi.MojangApi
import org.graphenedev.netty.packets.PacketHandler
import org.graphenedev.netty.packets.Packet

class LoginStartHandler : PacketHandler {
    override fun handle(channel: Channel, packet: Packet) {
        val username = packet.readString()
        LoginSuccessPacket(username, MojangApi.getUUIDofUsername(username)).sendPacket(channel)
    }
}