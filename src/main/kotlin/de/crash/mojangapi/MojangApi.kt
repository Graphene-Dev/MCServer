package de.crash.mojangapi

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.crash.util.formatToUUID
import java.net.URL
import java.util.*

fun getUUIDofUsername(value: String): UUID{
    val response = URL("https://api.mojang.com/users/profiles/minecraft/$value").readText()
    val rawUUID = jacksonObjectMapper().readTree(response)["id"].asText()
    return formatToUUID(rawUUID)
}