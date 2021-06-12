package netty

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import java.nio.charset.Charset

class PlayerChannelInboundHandler : SimpleChannelInboundHandler<Any>() {
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: Any?) {
        val buf = msg as ByteBuf
        val bytes = ByteArray(buf.readableBytes())
        buf.readBytes(bytes)
        println(bytes.toString(Charset.defaultCharset()))
        val cf = ctx!!.writeAndFlush(Unpooled.copiedBuffer("Test", Charset.defaultCharset()))
        cf.addListener(ChannelFutureListener.CLOSE)
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        super.channelActive(ctx)
        println("Channel connected -> " + ctx!!.channel())
    }
}