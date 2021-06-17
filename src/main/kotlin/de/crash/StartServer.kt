package de.crash

import de.crash.mc.nbt.testLoadNBT
import de.crash.netty.packets.initPacketHandlers
import de.crash.netty.startNettyServer
import kotlin.system.exitProcess

internal fun main(args: Array<String>) {
    initPacketHandlers()
    println("Loading Configs...")
    loadConfig()
    testLoadNBT()
    println("Configs loaded!\nStart Server...")
    startNettyServer() ?: exitProcess(0)
}