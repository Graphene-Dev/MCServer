package de.crash

import de.crash.mojangapi.MojangApi
import de.crash.netty.packets.initPacketHandlers
import de.crash.netty.startNettyServer
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlin.system.exitProcess

@OptIn(DelicateCoroutinesApi::class)
internal suspend fun main() {
    initPacketHandlers()
    if(!MojangApi.getStatus()) println("WARNING: YOU MAY FACE PROBLEMS WITH AUTHENTIFICATION AND JOINING THE SERVER! SOME MOJANG SERVER ARE UNAVAILABLE RIGHT NOW!")
    println("Loading Configs...")
    loadConfig()
    println("Configs loaded!\nStart Server...")
    startNettyServer() ?: exitProcess(0)
}