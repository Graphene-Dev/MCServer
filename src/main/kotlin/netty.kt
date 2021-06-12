import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.*
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import java.nio.charset.Charset

fun testServerNetty() {
    val epoll = Epoll.isAvailable()
    val eventLoopGroup = if(epoll) EpollEventLoopGroup() else NioEventLoopGroup()
    ServerBootstrap().group(eventLoopGroup).channel(if(epoll) EpollServerSocketChannel::class.java else NioServerSocketChannel::class.java)
        .childHandler(TestHandler())
        .bind(25565).sync().channel().closeFuture().syncUninterruptibly()
}

class TestHandler : ChannelInitializer<Channel>() {
    override fun initChannel(ch: Channel?) {
        ch!!.pipeline().addLast("default_channel_handler", TestInboundHandler())
    }
}

class TestInboundHandler : SimpleChannelInboundHandler<Any>() {
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