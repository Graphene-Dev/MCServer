import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.util.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import java.net.InetSocketAddress
import java.nio.charset.Charset
import java.util.concurrent.Executors

suspend fun main(args: Array<String>) {
    GlobalScope.launch { testServerNetty() }
    delay(1000)
    testClient()
}

suspend fun testServer() {
    println("Server is starting...")
    //Handle Args
    val server = aSocket(ActorSelectorManager(Executors.newCachedThreadPool().asCoroutineDispatcher())).tcp().bind(InetSocketAddress("127.0.0.1", 25565))
    while (true) {
        val socket = server.accept()
        println(socket.remoteAddress)
        val input = socket.openReadChannel()
        val output = socket.openWriteChannel(autoFlush = true)
        val line = input.readUTF8Line()

        println("received '$line' from ${socket.remoteAddress}")
        output.writeFully("$line back\r\n".toByteArray())
    }
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