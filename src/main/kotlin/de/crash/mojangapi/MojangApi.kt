package de.crash.mojangapi

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.crash.mojangapi.json.*
import de.crash.mojangapi.json.NameHistoryResponse
import de.crash.mojangapi.json.ProfileResponse
import de.crash.util.post
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

object MojangApi {
    fun getUUIDofUsername(value: String): UUID{
        val response = URL("https://api.mojang.com/users/profiles/minecraft/$value").readText()
        val rawUUID = jacksonObjectMapper().readValue<UsernameToUUIDResponse>(response).id
        return formatToUUID(rawUUID)
    }

    fun getNameHistory(value: UUID): Map<Long?, String> {
        val response = URL("https://api.mojang.com/user/profiles/${value}/names").readText()
        val obj = jacksonObjectMapper().readValue<NameHistoryResponse>(response)
        val resultMap = mutableMapOf<Long?, String>()
        obj.forEach {
            resultMap[it.changedToAt] = it.name
        }
        return resultMap
    }

    fun getProfileProperties(value: UUID): List<ProfileProperty> {
        val response = URL("https://sessionserver.mojang.com/session/minecraft/profile/${value}").readText()
        val obj = jacksonObjectMapper().readValue<ProfileResponse>(response)
        return obj.properties
    }

    fun getUUIDsOfUsernames(values: List<String>): HashMap<String, UUID>{
        val queryString = "[${values.joinToString { "\"$it\"," }.removeSuffix(",")}]"
        val resultMap = hashMapOf<String, UUID>()
        URL("https://api.mojang.com/profiles/minecraft").post(queryString){ resultString ->
            val obj = jacksonObjectMapper().readValue<UsernamesToUUIDsResponse>(resultString)
            obj.forEach {
                resultMap[values[0]] = formatToUUID(it.id)
            }
        }
        return resultMap
    }

    private fun formatToUUID(value: String): UUID {
        var i = 0
        val list = mutableListOf<String>()
        while (i < 32) {
            list.add(value.slice(i..i+3))
            i+=4
        }
        val uuidString = "${list[0]}${list[1]}-${list[2]}-${list[3]}-${list[4]}-${list[5]}${list[6]}${list[7]}"
        return UUID.fromString(uuidString)
    }
}