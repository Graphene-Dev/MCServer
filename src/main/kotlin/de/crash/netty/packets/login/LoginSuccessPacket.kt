package de.crash.netty.packets.login

import de.crash.mc.Server
import de.crash.mc.entities.entity.Player
import de.crash.netty.ClientStatus
import de.crash.netty.packets.Packet
import de.crash.netty.packets.SendPacket
import de.crash.netty.packets.nettyClients
import de.crash.netty.packets.play.JoinGamePacket
import de.crash.netty.packets.sendPacket
import io.netty.channel.Channel
import java.util.*

class LoginSuccessPacket(val username: String, val uuid: UUID): SendPacket {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(2)
        packet.write(uuid)
        packet.write(username)
        channel.sendPacket(packet)
        val nettyClient = nettyClients[channel]!!
        nettyClient.state = ClientStatus.PLAY
        val player = Player(nettyClient, username, uuid)
        Server.addPlayer(player)
        JoinGamePacket().sendPacket(channel)
    }
}