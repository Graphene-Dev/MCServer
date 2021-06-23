package org.graphenedev.netty.packets.play

import io.netty.channel.Channel
import org.graphenedev.Config
import org.graphenedev.netty.packets.*
import org.graphenedev.netty.packets.SendPacket
import org.graphenedev.util.sha256

class JoinGamePacket : SendPacket {
    override fun sendPacket(channel: Channel) {
        val packet = Packet(PacketType.JOIN_GAME)
        packet.writeAsVarInt(63) //Entity ID (EID)
        packet.write(Config.isHardcore) //isHardcore
        packet.write(Config.defaultGamemode) //Gamemode: 1 for Creative
        packet.write((-1).toByte()) //Previous Gamemode,-1 for none
        packet.writeAsVarInt(1) //Amount of worlds
        packet.write(Config.defaultWorldName) //Array of world names
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