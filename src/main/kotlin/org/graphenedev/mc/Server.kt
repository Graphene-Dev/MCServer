package org.graphenedev.mc

import io.netty.channel.Channel
import kotlinx.coroutines.*
import org.graphenedev.mc.event.MCEvent
import org.graphenedev.mc.event.ServerTickEvent
import org.graphenedev.mc.player.Player
import org.graphenedev.mc.world.Gamerules
import org.graphenedev.mc.world.World
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

    internal var lastTickFinished = true

    @OptIn(DelicateCoroutinesApi::class)
    internal fun serverTick(){
        loadWorlds()
        if(worlds.size == 0) {
            exitProcess(-1)
        }
        println("Worlds loaded!")
        GlobalScope.launch {
            var lastTick = System.currentTimeMillis()
            println("Server Tick Started!")
            while (true){
                if(System.currentTimeMillis() >= lastTick && lastTickFinished){
                    lastTick = System.currentTimeMillis() + 50
                    lastTickFinished = false
                    fireEvent(ServerTickEvent())
                }
            }
        }.invokeOnCompletion {
            println("SERVER TICK STOPPED, SERVER SHUT DOWN.")
            exitProcess(0)
        }
    }

    internal lateinit var gamerules: Gamerules

    fun getGamerules(): Gamerules = gamerules

    private fun loadWorlds(){
        WorldLoader.load()
    }

    fun fireEvent(mcEvent: MCEvent) {
        mcEvent.fire()
    }
}