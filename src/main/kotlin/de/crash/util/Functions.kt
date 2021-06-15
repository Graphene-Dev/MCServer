package de.crash.util

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import java.util.*

fun ByteArray.sha256(): ByteArray = MessageDigest.getInstance("SHA-256").digest(this)

fun URL.post(jsonInput: String, function: (response: String) -> Unit){
    val con = this.openConnection() as HttpURLConnection
    con.requestMethod = "POST"
    con.setRequestProperty("Content-Type", "application/json; utf-8")
    con.setRequestProperty("Accept", "application/json")
    con.doOutput = true
    val bytes = jsonInput.toByteArray()
    con.outputStream.write(bytes, 0, bytes.size)
    val br = BufferedReader(InputStreamReader(con.inputStream, "utf-8"))
    var responseLine: String
    val response = StringBuilder()
    while (br.readLine().also { responseLine = it } != null) {
        response.append(responseLine.trim { it <= ' ' })
    }
    function.invoke(response.toString())
}