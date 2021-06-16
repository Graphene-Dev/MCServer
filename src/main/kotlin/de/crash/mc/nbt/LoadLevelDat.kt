package de.crash.mc.nbt

import de.crash.mc.world.dimension.DimensionBiomeSourceData
import de.crash.mc.world.dimension.DimensionGeneratorData
import de.crash.mc.world.dimension.DimensionLevelData
import de.crash.util.getByte
import de.crash.util.getDouble
import de.crash.util.getInt
import de.crash.util.getLong
import me.nullicorn.nedit.NBTReader
import java.io.File
import java.util.*

internal fun testLoadNBT(){
    val nbtCompound = NBTReader.readFile(File("world/level.dat"))
    nbtCompound.run {
        val wanderingTraderSpawnChance = getInt("WanderingTraderSpawnChance")
        val borderCenterX = getDouble("BorderCenterX")
        val borderCenterZ = getDouble("BorderCenterZ")
        val difficulty = getByte("Difficulty", 2)
        val borderSizeLerpTime = getLong("BorderSizeLerpTime")
        val raining = getByte("raining")
        val time = getLong("Time")
        val gameType = getInt("GameType")
        val borderDamagePerBlock = getDouble("BorderDamagePerBlock", 0.2)
        val borderWarningsBlocks = getDouble("BorderWarningBlocks", 5.0)
        val borderSizeLerpTarget = getDouble("BorderSizeLerpTarget", 5.9999968E7)
        val worldGenCompound = getCompound("WorldGenSettings")
        val bonusChest = worldGenCompound.getByte("bonus_chest")
        val seed = worldGenCompound.getLong("seed", Random().nextLong())
        val generateFeatures = worldGenCompound.getByte("generate_features", 1)
        val dimensionsCompound = worldGenCompound.getCompound("dimensions")
        val dimensions = mutableListOf<DimensionLevelData>()
        dimensionsCompound.forEach {
            val dimensionCompound = dimensionsCompound.getCompound(it.key)
            val dimensionType = dimensionCompound.getString("type", "minecraft:overworld")
            val dimensionGeneratorCompound = dimensionCompound.getCompound("generator")
            val dimensionGeneratorSettings = dimensionGeneratorCompound.getString("settings", "minecraft:overworld")
            val dimensionGeneratorSeed = dimensionGeneratorCompound.getLong("seed", Random().nextLong())
            val dimensionGeneratorType = dimensionGeneratorCompound.getString("type", "minecraft:noise")
            val dimensionBiomeCompound = dimensionGeneratorCompound.getCompound("biome_source")
            val dimensionBiomeType = dimensionBiomeCompound.getString("type", "minecraft:vanilla_layered")
            val generatorBiomeLargeBiomes = dimensionBiomeCompound.getByte("large_biomes")
            dimensions.add(DimensionLevelData(it.key, dimensionType, DimensionGeneratorData(dimensionGeneratorSettings, dimensionGeneratorSeed, dimensionGeneratorType,
                DimensionBiomeSourceData(dimensionGeneratorSeed, dimensionBiomeType, generatorBiomeLargeBiomes))))
        }
        val dragonFightCompound = getCompound("DragonFight")
        val dragonFightGateways = mutableListOf<Int>()
        dragonFightCompound.getList("Gateways").forEachInt { dragonFightGateways.add(it) }
        val dragonFightNeedsStateScanning = dragonFightCompound.getByte("NeedsStateScanning", 1)
        val dragonKilled = dragonFightCompound.getByte("DragonKilled", 1)
        val dragonPreviouslyKilled = dragonFightCompound.getByte("PreviouslyKilled", 1)
        val versionCompound = getCompound("Version")
        val versionSnapshot = getByte("Snapshot")
        val versionId = getInt("Id", 2724)
        val versionName = getString("Name", "1.17")
        val dayTime = getLong("DayTime")
        val initialized = getByte("initialized", 1)
        val wasModded = getByte("WasModded")
        val allowCommands = getByte("allowCommands")
        val wanderingTraderSpawnDelay = getInt("WanderingTraderSpawnDelay", 24000)
        //Gamerules
    }
}