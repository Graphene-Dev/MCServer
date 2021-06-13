package de.crash.netty

import de.crash.netty.packets.nettyClients
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer

class PlayerChannelInitializer : ChannelInitializer<Channel>() {
    override fun initChannel(ch: Channel?) {
        ch!!.pipeline().addLast("handshake", PlayerChannelInboundHandler())
        nettyClients[ch] = NettyClient(ch, ch.id().asLongText(), ClientStatus.HANDSHAKE)
    }
}