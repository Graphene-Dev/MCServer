package de.crash.util

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection

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

fun URL.get(): String{
    val con: HttpsURLConnection = this.openConnection() as HttpsURLConnection
    con.requestMethod = "GET"
    con.setRequestProperty("Content-Type", "application/json")
    con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
    return con.inputStream.readBytes().toString(Charset.defaultCharset())
}

fun getCurrentTimeStamp(): String {
    val date = Calendar.getInstance().time
    val dateformat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    return dateformat.format(date).toString()
}