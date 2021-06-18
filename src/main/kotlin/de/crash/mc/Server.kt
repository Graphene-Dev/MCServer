package de.crash.mc

import de.crash.mc.entities.entity.Player
import de.crash.mc.event.MCEvent
import de.crash.mc.event.ServerTickEvent
import de.crash.mc.world.World
import io.netty.channel.Channel
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.system.exitProcess

object Server {
    //PLAYERS
    private val onlinePlayers: MutableList<Player> = mutableListOf()

    fun getOnlinePlayers(): List<Player> = onlinePlayers.toList()

    internal val channelMap: HashMap<Channel, Player> = hashMapOf()

    internal fun addPlayer(player: Player){
        onlinePlayers.add(player)
        channelMap[player.nettyClient.channel] = player
    }

    internal fun removePlayer(player: Player){
        onlinePlayers.remove(player)
        channelMap.remove(player.nettyClient.channel)
    }

    val worlds: HashMap<String, World> = hashMapOf()

    @OptIn(DelicateCoroutinesApi::class)
    internal fun serverTick(){
        GlobalScope.launch {
            var lastTick = System.currentTimeMillis()
            var lastTickJob = GlobalScope.launch { println("Server Tick started!") }
            while (true){
                if(System.currentTimeMillis() >= lastTick && lastTickJob.isCompleted){
                    lastTick = System.currentTimeMillis() + 50
                    lastTickJob = GlobalScope.launch { fireEvent(ServerTickEvent()) }
                }
            }
        }.invokeOnCompletion {
            println("SERVER TICK STOPPED, SERVER SHUT DOWN.")
            exitProcess(0)
        }
    }

    fun fireEvent(mcEvent: MCEvent) = mcEvent.fire()
}