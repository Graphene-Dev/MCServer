package de.crash

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

data class PropertiesObj(val port: Int, val server_ip: String)

private val defaultConfig = PropertiesObj(25565, "127.0.0.1")
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