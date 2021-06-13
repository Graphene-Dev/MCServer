package de.crash.netty.packets.status

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.crash.Config
import de.crash.json.*
import de.crash.netty.packets.Packet
import de.crash.netty.packets.sendPacket
import de.crash.protocolId
import de.crash.version
import io.netty.channel.Channel

class ResponsePacket {
    fun sendPacket(channel: Channel){
        val packet = Packet(0)
        val serverStatusObj = ServerStatusObj(ServerStatusVersion(version, protocolId),
            ServerStatusPlayers(Config.maxPlayers.toLong(), 2, listOf()),
            ServerStatusDescription(Config.motd))
        val jsonString = jacksonObjectMapper().writeValueAsString(serverStatusObj)
        packet.write(jsonString)
        channel.sendPacket(packet)
    }
}