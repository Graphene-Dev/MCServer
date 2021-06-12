package netty

import io.netty.channel.*

class PlayerChannelInitializer : ChannelInitializer<Channel>() {
    override fun initChannel(ch: Channel?) {
        ch!!.pipeline().addLast("default_channel_handler", PlayerChannelInboundHandler())
    }
}