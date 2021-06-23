package org.graphenedev.netty.packets.play

import io.netty.channel.Channel
import org.graphenedev.Config
import org.graphenedev.mc.Server
import org.graphenedev.netty.packets.*
import org.graphenedev.netty.packets.PacketSender
import org.graphenedev.util.sha256

class JoinGamePacket(private val previousGamemode: Byte = -1, private val entityId: Int = 63) : PacketSender {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(PacketType.JOIN_GAME)
        packet.writeAsVarInt(entityId) //Entity ID (EID)
        packet.write(Config.isHardcore) //isHardcore
        packet.write(Config.defaultGamemode) //Gamemode: 1 for Creative
        packet.write(previousGamemode) //Previous Gamemode,-1 for none
        packet.writeAsVarInt(Server.worlds.size) //Amount of worlds
        Server.worlds.forEach {
            packet.write(it.key) //Array of world names
        }
        //Dimension Codec type of NBT Tag Compound
        //Dimension type of NBT Tag Compound
        packet.write(Config.defaultWorldName) //Name of world to spawn into
        packet.write(Config.seed.toByteArrayAsVarLong().toByteArray().sha256().slice(0..7).toByteArray()) //HashedSeed type of Long -> First 8 Bytes of sha256 of seed
        packet.writeAsVarInt(Config.maxPlayers) //Max Players
        packet.writeAsVarInt(Config.viewDistance) //View Distance
        packet.write(false) //reduced debug info
        packet.write(true) //enable respawn screen
        packet.write(false) //is debug
        packet.write(false) //is flat
        channel.sendPacket(packet)
    }
}