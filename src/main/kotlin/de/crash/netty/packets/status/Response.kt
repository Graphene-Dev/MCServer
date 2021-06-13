package de.crash.netty.packets.status

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.crash.json.ServerStatusDescription
import de.crash.json.ServerStatusObj
import de.crash.json.ServerStatusPlayers
import de.crash.json.ServerStatusVersion
import de.crash.netty.packets.Packet
import de.crash.protocolId
import de.crash.version
import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import io.netty.channel.ChannelFutureListener

class Response {
    fun sendPacket(channel: Channel){
        val packet = Packet(0)
        val serverStatusObj = ServerStatusObj(ServerStatusVersion(version, protocolId), ServerStatusPlayers(50, 2, listOf()),
            ServerStatusDescription("This is a test Server"))
        val jsonString = jacksonObjectMapper().writeValueAsString(serverStatusObj)
        println(jsonString)
        packet.write(jsonString)
        val bytes = packet.getPacketBytes()
        val newPacket = Packet(bytes)
        println("Length: " + newPacket.readVarInt())
        println("PacketId: " + newPacket.readVarInt())
        println("Json: " + newPacket.readString())
        val cf = channel.writeAndFlush(Unpooled.copiedBuffer(bytes))
        cf.addListener(ChannelFutureListener.CLOSE)
    }
}