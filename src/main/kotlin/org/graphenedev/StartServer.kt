package org.graphenedev

import kotlinx.coroutines.DelicateCoroutinesApi
import org.graphenedev.mojangapi.MojangApi
import org.graphenedev.netty.packets.initPacketHandlers
import org.graphenedev.netty.startNettyServer
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