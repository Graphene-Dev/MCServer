package de.crash.netty.packets.status

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.crash.json.*
import de.crash.netty.packets.Packet
import de.crash.protocolId
import de.crash.version
import io.netty.buffer.Unpooled
import io.netty.channel.Channel
import io.netty.channel.ChannelFutureListener

class Response {
    fun sendPacket(channel: Channel){
        val packet = Packet(0)
        val serverStatusObj = ServerStatusObj(ServerStatusVersion(version, protocolId),
            ServerStatusPlayers(50, 2, listOf()),
            ServerStatusDescription("This is a test Server"))
        val jsonString = jacksonObjectMapper().writeValueAsString(serverStatusObj)
        packet.write(jsonString)
        val cf = channel.writeAndFlush(Unpooled.copiedBuffer(packet.getPacketBytes()))
        cf.addListener(ChannelFutureListener.CLOSE)
    }
}