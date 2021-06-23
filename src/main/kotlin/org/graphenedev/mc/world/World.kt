package org.graphenedev.mc.world

import me.nullicorn.nedit.type.NBTCompound
import org.graphenedev.mc.nbt.*
import org.graphenedev.mc.world.dimension.DimensionType
import org.graphenedev.mc.world.generator.BiomeSourceType
import org.graphenedev.mc.world.generator.GeneratorSettings
import org.graphenedev.util.toMCEnumString
import java.io.File
import java.util.*

class World
internal constructor(nbtCompound: NBTCompound, worldFolder: File, val name: String) {
    val seed: Long
    val bonusChest: Boolean
    val worldBorder: Worldborder
    var time: Long
    var dayTime: Long
    var wanderingTraderSpawnChance: Int
    var wanderingTraderSpawnDelay: Int
    val weather: Weather
    var spawnLoc: Location
    val type: DimensionType
    val genSettings: GeneratorSettings
    val biomeSourceType: BiomeSourceType
    val largeBiomes: Boolean

    init {
        nbtCompound.run {
            val worldGenCompound = getCompound("WorldGenSettings")
            bonusChest = worldGenCompound.getByte("bonus_chest").asBoolean()
            seed = worldGenCompound.getLong("seed", Random().nextLong())
            val worldDimensionCompound = worldGenCompound.getCompound("dimensions").getCompound(name)
            type = DimensionType.valueOf(worldDimensionCompound.getString("type", "minecraft:overworld").toMCEnumString())
            val generatorCompound = worldDimensionCompound.getCompound("generator")
            genSettings = GeneratorSettings.valueOf(generatorCompound.getString("settings", "minecraft:overworld").toMCEnumString())
            val biomeSourceCompound = generatorCompound.getCompound("biome_source")
            biomeSourceType = BiomeSourceType.valueOf(biomeSourceCompound.getString("type", "minecraft:vanilla_layered").toMCEnumString())
            largeBiomes = biomeSourceCompound.getByte("large_biomes").asBoolean()
            worldBorder = Worldborder(this)
            time = getLong("Time")
            wanderingTraderSpawnChance = getInt("WanderingTraderSpawnChance")
            wanderingTraderSpawnDelay = getInt("WanderingTraderSpawnDelay", 24000)
            dayTime = getLong("DayTime")
            weather = Weather(this)
            val spawnX = getInt("SpawnX")
            val spawnY = getInt("SpawnY", 70)
            val spawnZ = getInt("SpawnZ")
            val spawnAngle = getFloat("SpawnAngle")
            spawnLoc = Location(this@World, spawnX.toDouble(), spawnY.toDouble(), spawnZ.toDouble(), spawnAngle, 0F)
            //val dragonFightCompound = getCompound("DragonFight")
            //val dragonFightGateways = mutableListOf<Int>()
            //dragonFightCompound.getList("Gateways").forEachInt { dragonFightGateways.add(it) }
            //val dragonFightNeedsStateScanning = dragonFightCompound.getByte("NeedsStateScanning", 1)
            //val dragonKilled = dragonFightCompound.getByte("DragonKilled", 1)
            //val dragonPreviouslyKilled = dragonFightCompound.getByte("PreviouslyKilled", 1)
            //val versionCompound = getCompound("Version")
            //val versionSnapshot = versionCompound.getByte("Snapshot").asBoolean()
            //val versionId = versionCompound.getInt("Id", 2724)
            //val versionName = versionCompound.getString("Name", "1.17")
            //val initialized = getByte("initialized").asBoolean()
            //val wasModded = getByte("WasModded").asBoolean()
            //val version = getInt("version", 19133)
            //val lastPlayed = getLong("LastPlayed", System.currentTimeMillis())
            //val dataVersion = getInt("DataVersion", 2724)
        }
    }

    companion object {
        const val MAX_WORLD_HEIGHT = 256
    }
}