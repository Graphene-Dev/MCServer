package netty

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel

val epoll = Epoll.isAvailable()
const val port = 25565

fun startNettyServer(){
    val eventLoopGroup = if(epoll) EpollEventLoopGroup() else NioEventLoopGroup()
    ServerBootstrap()
        .group(eventLoopGroup)
        .channel(if(epoll) EpollServerSocketChannel::class.java else NioServerSocketChannel::class.java)
        .childHandler(PlayerChannelInitializer())
        .bind(port)
        .sync()
        .channel()
        .closeFuture()
        .syncUninterruptibly()
}