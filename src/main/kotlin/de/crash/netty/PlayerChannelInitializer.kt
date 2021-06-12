package de.crash.netty

import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer

class PlayerChannelInitializer : ChannelInitializer<Channel>() {
    override fun initChannel(ch: Channel?) {
        ch!!.pipeline().addLast("default_channel_handler", PlayerChannelInboundHandler())
    }
}