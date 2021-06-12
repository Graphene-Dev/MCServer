package de.crash

import de.crash.netty.startNettyServer
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    println("Loading Configs...")
    loadConfig()
    println("Configs loaded!\nStart Server...")
    startNettyServer() ?: exitProcess(0)
}