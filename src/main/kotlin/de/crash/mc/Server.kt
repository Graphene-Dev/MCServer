package de.crash.mc

import de.crash.mc.entities.entity.Player
import de.crash.mc.event.MCEvent
import de.crash.mc.event.ServerTickEvent
import de.crash.mc.world.World
import io.netty.channel.Channel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    internal fun serverTick(){
        GlobalScope.launch {
            while (true){
                fireEvent(ServerTickEvent())
                delay(50)
            }
        }.invokeOnCompletion {
            println("SERVER TICK STOPPED, SERVER SHUT DOWN.")
            exitProcess(0)
        }
    }

    internal fun fireEvent(mcEvent: MCEvent) {
        GlobalScope.launch { mcEvent.fire() }
    }
}