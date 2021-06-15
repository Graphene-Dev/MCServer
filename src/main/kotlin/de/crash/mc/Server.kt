package de.crash.mc

import de.crash.mc.entities.entity.Player
import io.netty.channel.Channel
import net.benwoodworth.knbt.Nbt
import net.benwoodworth.knbt.NbtVariant

object Server {
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

    val NBT = Nbt {
        variant = NbtVariant.Java
        compression = null
        compressionLevel = null
        encodeDefaults = false
        ignoreUnknownKeys = false
    }
}