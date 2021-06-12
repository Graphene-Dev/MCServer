import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.asCoroutineDispatcher
import java.net.InetSocketAddress
import java.util.concurrent.Executors

suspend fun main(args: Array<String>) {
    println("Server is starting...")
    //Handle Args
    val server = aSocket(ActorSelectorManager(Executors.newCachedThreadPool().asCoroutineDispatcher())).tcp().bind(InetSocketAddress("127.0.0.1", 25565))
}