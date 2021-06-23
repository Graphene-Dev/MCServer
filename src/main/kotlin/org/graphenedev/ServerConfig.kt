package org.graphenedev

import kotlin.system.exitProcess

data class PropertiesObj(val port: Int, val server_ip: String, val online_mode: Boolean, val pvp: Boolean, val maxPlayers: Int, val whitelist: Boolean,
    val motd: String, val viewDistance: Int, val defaultWorldName: String, val defaultGamemode: Byte, val seed: Long, val allowCommands: Boolean,
    val genStructures: Boolean, val isHardcore: Boolean)

internal val defaultConfig = PropertiesObj(
    port = 25565,
    server_ip = "127.0.0.1",
    online_mode = true,
    pvp = true,
    maxPlayers = 50,
    whitelist = false,
    motd = "§2 This is a test server in Kotlin",
    viewDistance = 8,
    defaultWorldName = "world",
    defaultGamemode = 1,
    seed = 5859195333423694798,
    allowCommands = true,
    genStructures = true,
    isHardcore = false
)

var Config: PropertiesObj = defaultConfig
const val protocolId: Long = 755
const val version = "1.17"

internal fun loadConfig(){
    Config = getConfigData()
    if(!hasEulaAccepted()) {
        println("YOU MUST ACCEPT THE EULA!")
        exitProcess(-1)
    }
}