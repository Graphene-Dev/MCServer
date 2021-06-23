package org.graphenedev.mc

import me.nullicorn.nedit.NBTReader
import me.nullicorn.nedit.NBTWriter
import me.nullicorn.nedit.type.NBTCompound
import org.graphenedev.mc.world.Gamerules
import org.graphenedev.mc.world.World
import org.graphenedev.mc.world.dimension.DimensionType
import org.graphenedev.netty.packets.Packet
import org.graphenedev.util.GZip
import org.graphenedev.util.ZLib
import java.io.File

object WorldLoader {
    fun load(){
        File(".").listFiles()?.forEach { file ->
            if(file.isDirectory){
                if(!file.listFiles()?.filter { it.isFile && it.name == "level.dat" }.isNullOrEmpty()){
                    loadWorld(file)
                }
            }
        }
    }

    fun loadWorld(worldFolder: File){
        try {
            val levelDat = File(worldFolder.path + "/level.dat")
            val compound = NBTReader.readFile(levelDat).getCompound("Data")
            Server.gamerules = Gamerules(compound.getCompound("GameRules"))
            compound.getCompound("WorldGenSettings").getCompound("dimensions").forEach { entry ->
                println("Loading world \"${entry.key}\"...")
                val world = World(compound, worldFolder, entry.key)
                val regionFolder = when {
                    world.type == DimensionType.THE_NETHER && File(worldFolder.path + "/DIM-1").exists() -> File(worldFolder.path + "/DIM-1/region")
                    else -> File(worldFolder.path + "/region")
                }
                regionFolder.listFiles()?.forEach { file ->
                    val nameArray = file.name.split(".")
                    val posX = nameArray[1].toInt()
                    val posZ = nameArray[2].toInt()
                    val bytePacket = Packet(file.readBytes())
                    for (i in 0..1023){
                        bytePacket.readPos = i*4
                        val rawInt = bytePacket.readInt()
                        if(rawInt == 0) continue
                        val offset = rawInt shr 8 and 0xFFFFFF
                        val size = rawInt and 0xFF
                        bytePacket.readPos = i*8
                        val epochSeconds = bytePacket.readInt().toLong()
                        bytePacket.readPos = offset*4096
                        val dataLength = bytePacket.readInt()
                        val compressionType = bytePacket.readByte()
                        val compressedBytes = bytePacket.readBytes(dataLength)
                        val uncompressedBytes = when(compressionType){
                            1.toByte() -> GZip.decompress(compressedBytes)
                            else -> ZLib.decompress(compressedBytes)
                        }
                    }
                }
                Server.worlds[entry.key] = world
                println("\"${entry.key}\" loaded!")
            }
        }catch (ex: Exception) {
            println("ERROR WHILE LOADING WORLD, REPORT IT TO THE DEVS WITH FOLLOWING STACKTRACE:\n")
            ex.printStackTrace()
        }
    }

    fun loadDimension(folder: File){

    }
}