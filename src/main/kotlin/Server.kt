import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.util.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import netty.startNettyServer
import java.net.InetSocketAddress
import java.nio.charset.Charset

suspend fun main(args: Array<String>) {
    GlobalScope.launch { startNettyServer() }
    delay(1000)
    testClient()
}

suspend fun testClient() {
    val socket = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp()
        .connect(InetSocketAddress("127.0.0.1", 25565))
    val input = socket.openReadChannel()
    val output = socket.openWriteChannel(autoFlush = true)
    output.writeFully("hello".toByteArray())
    input.awaitContent()
    println("Server said: '${input.toByteArray().toString(Charset.defaultCharset())}'")
}