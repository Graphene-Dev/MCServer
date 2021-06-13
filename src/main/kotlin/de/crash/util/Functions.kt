package de.crash.util

import java.security.MessageDigest
import java.util.*

fun formatToUUID(value: String): UUID {
    var i = 0
    val list = mutableListOf<String>()
    while (i < 32) {
        list.add(value.slice(i..i+3))
        i+=4
    }
    val uuidString = "${list[0]}${list[1]}-${list[2]}-${list[3]}-${list[4]}-${list[5]}${list[6]}${list[7]}"
    return UUID.fromString(uuidString)
}

fun ByteArray.sha256(): ByteArray = MessageDigest.getInstance("SHA-256").digest(this)