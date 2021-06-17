package de.crash

import de.crash.mc.Server
import de.crash.netty.packets.initPacketHandlers
import de.crash.netty.startNettyServer
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlin.system.exitProcess

@OptIn(DelicateCoroutinesApi::class)
internal fun main() {
    initPacketHandlers()
    println("Loading Configs...")
    loadConfig()
    println("Configs loaded!\nStart Server...")
    Server.serverTick()
    startNettyServer() ?: exitProcess(0)
}