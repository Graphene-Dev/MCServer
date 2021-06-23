package org.graphenedev.netty.packets.play

import io.netty.channel.Channel
import org.graphenedev.mc.Server
import org.graphenedev.netty.packets.PacketHandler
import org.graphenedev.netty.packets.Packet

class ChatMessageHandler() : PacketHandler {
    override fun handle(channel: Channel, packet: Packet) {
        val message = packet.readString()
        if(message.startsWith("/")){
            //TODO: Command
            return
        }
        Server.getOnlinePlayers().forEach {
            //TODO: Send Message to all player
        }
    }
}