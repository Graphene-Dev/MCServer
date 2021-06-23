package org.graphenedev.netty.packets.status

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.netty.channel.Channel
import org.graphenedev.Config
import org.graphenedev.mojangapi.json.ServerStatusDescription
import org.graphenedev.mojangapi.json.ServerStatusPlayers
import org.graphenedev.mojangapi.json.ServerStatusResponse
import org.graphenedev.mojangapi.json.ServerStatusVersion
import org.graphenedev.netty.packets.Packet
import org.graphenedev.netty.packets.PacketType
import org.graphenedev.netty.packets.PacketSender
import org.graphenedev.netty.packets.sendPacket
import org.graphenedev.protocolId
import org.graphenedev.version

class ResponsePacket : PacketSender {
    override fun sendPacket(channel: Channel){
        val packet = Packet(PacketType.RESPONSE)
        val serverStatusObj = ServerStatusResponse(
            ServerStatusVersion(version, protocolId),
            ServerStatusPlayers(Config.maxPlayers.toLong(), 2, listOf()),
            ServerStatusDescription(Config.motd)
        )
        val jsonString = jacksonObjectMapper().writeValueAsString(serverStatusObj)
        packet.write(jsonString)
        channel.sendPacket(packet)
    }
}