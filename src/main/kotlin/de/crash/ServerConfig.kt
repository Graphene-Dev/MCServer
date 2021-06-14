package de.crash

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

data class PropertiesObj(val port: Int, val server_ip: String, val online_mode: Boolean, val pvp: Boolean, val maxPlayers: Int, val whitelist: Boolean,
    val motd: String, val viewDistance: Int, val defaultWorldName: String, val defaultGamemode: Byte, val seed: Long)

private val defaultConfig = PropertiesObj(
    port = 25565,
    server_ip = "127.0.0.1",
    online_mode = true,
    pvp = true,
    maxPlayers = 50,
    whitelist = false,
    motd = "§2 This is a test server in Kotlin | github.com/Crashcrafter/MCServer",
    viewDistance = 8,
    defaultWorldName = "world",
    defaultGamemode = 1,
    seed = 5859195333423694798
)

var Config: PropertiesObj = defaultConfig
const val protocolId: Long = 755
const val version = "1.17"

fun loadConfig(){
    val file = File("properties.json")
    if(!file.exists()) {
        file.createNewFile()
        jacksonObjectMapper().writeValue(file, defaultConfig)
        println("Properties.json not found, creating default configuration!")
        return
    }
    Config = jacksonObjectMapper().readValue(file)
}