package de.crash.mc

import de.crash.mc.entities.entity.Player
import io.netty.channel.Channel

object Server {
    val onlinePlayers: MutableList<Player> = mutableListOf()

    private val channelMap: HashMap<Channel, Player> = hashMapOf()

    internal fun addPlayer(player: Player){
        onlinePlayers.add(player)
        channelMap[player.nettyClient.channel] = player
    }

    internal fun removePlayer(player: Player){
        onlinePlayers.remove(player)
        channelMap.remove(player.nettyClient.channel)
    }
}