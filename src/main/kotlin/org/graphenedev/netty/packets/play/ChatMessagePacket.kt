package org.graphenedev.netty.packets.play

import io.netty.channel.Channel
import org.graphenedev.mc.world.Difficulty
import org.graphenedev.netty.packets.Packet
import org.graphenedev.netty.packets.PacketType
import org.graphenedev.netty.packets.PacketSender
import org.graphenedev.netty.packets.sendPacket
import java.util.*


class ChatMessagePacket(private val JSONdata: String, private var position: Int, private var sender : UUID) : PacketSender {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(PacketType.CHAT_MESSAGE)
        packet.write(JSONdata)
        packet.write(position.toByte())
        packet.write(sender)

        channel.sendPacket(packet)
    }
}