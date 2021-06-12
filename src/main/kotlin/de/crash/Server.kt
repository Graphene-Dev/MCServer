package de.crash

import de.crash.netty.packets.initPacketHandlers
import de.crash.netty.startNettyServer
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    initPacketHandlers()
    println("Loading Configs...")
    loadConfig()
    println("Configs loaded!\nStart Server...")
    startNettyServer() ?: exitProcess(0)
}