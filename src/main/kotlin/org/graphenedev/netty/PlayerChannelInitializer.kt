package org.graphenedev.netty

import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import org.graphenedev.netty.packets.nettyClients

internal class PlayerChannelInitializer : ChannelInitializer<Channel>() {
    override fun initChannel(ch: Channel?) {
        ch!!.pipeline().addLast("handshake", PlayerChannelInboundHandler())
        nettyClients[ch] = NettyClient(ch, ch.id().asLongText(), ClientStatus.HANDSHAKE)
    }
}