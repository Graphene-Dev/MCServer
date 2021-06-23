package org.graphenedev.netty.packets.play

import io.netty.channel.Channel
import org.graphenedev.netty.packets.Packet
import org.graphenedev.netty.packets.PacketType
import org.graphenedev.netty.packets.PacketSender
import org.graphenedev.netty.packets.sendPacket

class ServerDifficultyPacket(private val difficulty: Int = 2) : PacketSender {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(PacketType.DIFFICULTY)
        packet.write(difficulty.toByte())  //Difficulty, 2 = normal
        packet.write(false)       //Difficulty locked
        channel.sendPacket(packet)
    }
}