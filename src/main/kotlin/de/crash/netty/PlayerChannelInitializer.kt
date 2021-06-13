package de.crash.netty

import de.crash.netty.packets.nettyPlayers
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer

class PlayerChannelInitializer : ChannelInitializer<Channel>() {
    override fun initChannel(ch: Channel?) {
        ch!!.pipeline().addLast("handshake", PlayerChannelInboundHandler())
        nettyPlayers[ch] = NettyClient(ch, ch.id().asLongText(), ClientStatus.HANDSHAKE)
    }
}