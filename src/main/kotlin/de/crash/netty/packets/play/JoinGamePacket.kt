package de.crash.netty.packets.play

import de.crash.Config
import de.crash.netty.packets.Packet
import de.crash.netty.packets.getBytes
import de.crash.netty.packets.sendPacket
import de.crash.util.sha256
import io.netty.channel.Channel

class JoinGamePacket {
    fun sendPacket(channel: Channel) {
        //Assign Netty Player to Player
        val packet = Packet(26)
        packet.write(63) //Entity ID (EID)
        packet.write(false) //isHardcore
        packet.write(1.toByte()) //Gamemode: 1 for Creative
        packet.write((-1).toByte()) //Previous Gamemode,-1 for none
        packet.write(1) //Amount of worlds
        packet.write(Config.defaultWorldName) //Array of world names
        //Dimension Codec type of NBT Tag Compound
        //Dimension type of NBT Tag Compound
        packet.write(Config.defaultWorldName) //Name of world to spawn into
        packet.write(Config.seed.getBytes().toByteArray().sha256().slice(0..7).toByteArray()) //HashedSeed type of Long -> First 8 Bytes of sha256 of seed
        packet.write(Config.maxPlayers) //Max Players
        packet.write(Config.viewDistance) //View Distance
        packet.write(false) //reduced debug info
        packet.write(true) //enable respawn screen
        packet.write(false) //is debug
        packet.write(false) //is flat
        channel.sendPacket(packet)
    }
}