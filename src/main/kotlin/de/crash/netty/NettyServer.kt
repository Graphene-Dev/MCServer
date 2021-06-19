package de.crash.netty

import de.crash.Config
import de.crash.mc.Server
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import java.net.BindException
import kotlin.system.exitProcess

private val epoll = Epoll.isAvailable()

internal fun startNettyServer(): ChannelFuture? {
    return try {
        ServerBootstrap()
            .group(if(epoll) EpollEventLoopGroup() else NioEventLoopGroup())
            .channel(if(epoll) EpollServerSocketChannel::class.java else NioServerSocketChannel::class.java)
            .childHandler(PlayerChannelInitializer())
            .bind(Config.server_ip, Config.port)
            .sync().addListener {
                if(it.isSuccess) {
                    println("Netty Server started!")
                    Server.serverTick()
                } else {
                    println("Netty Server failed!")
                    exitProcess(-1)
                }
            }
    }catch (ex: BindException) {
        println("This address or port is already in use!\nStacktrace:")
        ex.printStackTrace()
        null
    }catch (ex: Exception) {
        println("THIS IS A BUG, REPORT IT TO THE DEVS (https://github.com/Graphene-Dev/MCServer/issues/new) WITH THE FOLLOWING STACKTRACE:")
        ex.printStackTrace()
        null
    }
}