package de.crash.netty

import de.crash.netty.packets.statusMap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer

class PlayerChannelInitializer : ChannelInitializer<Channel>() {
    override fun initChannel(ch: Channel?) {
        ch!!.pipeline().addLast("handshake", PlayerChannelInboundHandler())
        statusMap[ch.id().asLongText()] = 0
    }
}