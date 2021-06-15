package de.crash

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.crash.util.getCurrentTimeStamp
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun createDirIfNotExists(pathname: String){
    val folder = File(pathname)
    if(folder.notExists()){
        folder.mkdir()
    }
}

fun createFileIfNotExists(file: File): File = createFileIfNotExists(file.path)

fun createFileIfNotExists(pathname: String) : File {
    val file = File(pathname)
    if(file.notExists()){
        file.createNewFile()
    }
    return file
}

fun File.notExists() = !this.exists()

internal fun hasEulaAccepted(): Boolean {
    val file = File("eula.txt")
    if(file.notExists()){
        createFileIfNotExists(file).writeText("#By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula).\n" +
                "#${getCurrentTimeStamp()}\n" +
                "{\"eula\": false}")
        return false
    }
    file.readLines().forEach {
        if(it.startsWith("{") && it.endsWith("}")){
            return jacksonObjectMapper().readTree(it)["eula"].asBoolean()
        }
    }
    return false
}

internal fun getConfigData(): PropertiesObj {
    val file = File("properties.json")
    if(!file.exists()) {
        file.createNewFile()
        jacksonObjectMapper().writeValue(file, defaultConfig)
        println("Properties.json not found, creating default configuration!")
        return defaultConfig
    }
    return jacksonObjectMapper().readValue(file)
}