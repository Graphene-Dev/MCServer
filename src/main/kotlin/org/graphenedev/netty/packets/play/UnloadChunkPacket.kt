package org.graphenedev.netty.packets.play


import io.netty.channel.Channel
import org.graphenedev.mc.world.Difficulty
import org.graphenedev.netty.packets.Packet
import org.graphenedev.netty.packets.PacketType
import org.graphenedev.netty.packets.PacketSender
import org.graphenedev.netty.packets.sendPacket

class UnloadChunkPacket(private var chunkX : Int, private var chunkY : Int) : PacketSender {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(PacketType.CHAT_MESSAGE)
        packet.writeAsVarInt(chunkX)
        packet.writeAsVarInt(chunkY)

        channel.sendPacket(packet)
    }
}