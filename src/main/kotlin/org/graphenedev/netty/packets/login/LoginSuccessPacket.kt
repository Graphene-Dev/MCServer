package org.graphenedev.netty.packets.login

import io.netty.channel.Channel
import org.graphenedev.mc.Server
import org.graphenedev.mc.player.Player
import org.graphenedev.netty.ClientStatus
import org.graphenedev.netty.packets.Packet
import org.graphenedev.netty.packets.SendPacket
import org.graphenedev.netty.packets.nettyClients
import org.graphenedev.netty.packets.play.JoinGamePacket
import org.graphenedev.netty.packets.sendPacket
import java.util.*

class LoginSuccessPacket(private val username: String, private val uuid: UUID): SendPacket {
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