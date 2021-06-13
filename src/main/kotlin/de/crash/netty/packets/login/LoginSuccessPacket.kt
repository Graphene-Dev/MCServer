package de.crash.netty.packets.login

import de.crash.netty.ClientStatus
import de.crash.netty.packets.Packet
import de.crash.netty.packets.nettyPlayers
import de.crash.netty.packets.play.JoinGamePacket
import de.crash.netty.packets.sendPacket
import io.netty.channel.Channel
import java.util.*

class LoginSuccessPacket(val username: String, val uuid: UUID) {
    fun sendPacket(channel: Channel) {
        val packet = Packet(2)
        packet.write(uuid)
        packet.write(username)
        channel.sendPacket(packet)
        nettyPlayers[channel]!!.state = ClientStatus.PLAY
        JoinGamePacket().sendPacket(channel)
    }
}