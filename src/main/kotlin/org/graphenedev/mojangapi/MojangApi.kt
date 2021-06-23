package org.graphenedev.mojangapi

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.graphenedev.mojangapi.json.*
import org.graphenedev.util.post
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import org.graphenedev.mojangapi.json.NameHistoryResponse
import org.graphenedev.mojangapi.json.ProfileProperty
import org.graphenedev.mojangapi.json.ProfileResponse
import org.graphenedev.mojangapi.json.UsernameToUUIDResponse
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

object MojangApi {
    suspend fun getStatus(): Boolean {
        val response = URL("https://status.mojang.com/check").readText()
        val obj = jacksonObjectMapper().readTree(response)
        var result = true
        obj.elements().forEach {
            val node = it.fields().asFlow().first()
            if(node.key != "sessionserver.mojang.com" && node.value.asText() != "green"){
                result = false
            }
        }
        return result
    }

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