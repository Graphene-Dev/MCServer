package de.crash.mc

import de.crash.mc.entities.entity.Player
import de.crash.mc.world.World
import io.netty.channel.Channel

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
}