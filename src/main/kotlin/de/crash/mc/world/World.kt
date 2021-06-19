package de.crash.mc.world

import de.crash.mc.Server
import de.crash.mc.nbt.*
import de.crash.mc.player.Gamemode
import de.crash.mc.world.dimension.DimensionBiomeSourceData
import de.crash.mc.world.dimension.DimensionGeneratorData
import de.crash.mc.world.dimension.DimensionLevelData
import de.crash.mc.world.dimension.Level
import me.nullicorn.nedit.type.NBTCompound
import java.util.*

class World
internal constructor(nbtCompound: NBTCompound) {
    val baseName: String
    val difficulty: Difficulty
    val gameType: Gamemode
    val gamerules: Gamerules
    val levels = mutableListOf<Level>()
    val seed: Long
    val generateStructures: Boolean
    val bonusChest: Boolean
    val hardcore: Boolean
    val difficultyLocked: Boolean
    init {
        nbtCompound.run {
            baseName = getString("LevelName", "world")
            difficulty = when(getByte("Difficulty", 2)){
                0.toByte() -> Difficulty.PEACEFUL
                1.toByte() -> Difficulty.EASY
                2.toByte() -> Difficulty.NORMAL
                3.toByte() -> Difficulty.HARD
                else -> Difficulty.NORMAL
            }
            gameType = when(getInt("GameType")){
                0 -> Gamemode.SURVIVAL
                1 -> Gamemode.CREATIVE
                2 -> Gamemode.ADVENTURE
                3 -> Gamemode.SPECTATOR
                else -> Gamemode.SURVIVAL
            }
            gamerules = Gamerules(getCompound("GameRules"))
            val worldGenCompound = getCompound("WorldGenSettings")
            bonusChest = worldGenCompound.getByte("bonus_chest").asBoolean()
            seed = worldGenCompound.getLong("seed", Random().nextLong())
            generateStructures = worldGenCompound.getByte("generate_features", 1).asBoolean()
            hardcore = getByte("hardcore").asBoolean()
            difficultyLocked = getByte("DifficultyLocked").asBoolean()
            val dimensionsCompound = worldGenCompound.getCompound("dimensions")
            dimensionsCompound.forEach {
                levels.add(Level(dimensionsCompound.getCompound(it.key)))
            }
            val wanderingTraderSpawnChance = getInt("WanderingTraderSpawnChance")
            val wanderingTraderSpawnDelay = getInt("WanderingTraderSpawnDelay", 24000)
            val borderCenterX = getDouble("BorderCenterX")
            val borderCenterZ = getDouble("BorderCenterZ")
            val borderSizeLerpTime = getLong("BorderSizeLerpTime")
            val borderDamagePerBlock = getDouble("BorderDamagePerBlock", 0.2)
            val borderWarningsBlocks = getDouble("BorderWarningBlocks", 5.0)
            val borderSizeLerpTarget = getDouble("BorderSizeLerpTarget", 5.9999968E7)
            val borderSafeZone = getDouble("BorderSafeZone", 5.0)
            val borderWarningTime = getDouble("BorderWarningTime", 15.0)
            val borderSize = getDouble("BorderSize", 5.9999968E7)
            val raining = getByte("raining").asBoolean()
            val time = getLong("Time")
            val dragonFightCompound = getCompound("DragonFight")
            val dragonFightGateways = mutableListOf<Int>()
            dragonFightCompound.getList("Gateways").forEachInt { dragonFightGateways.add(it) }
            val dragonFightNeedsStateScanning = dragonFightCompound.getByte("NeedsStateScanning", 1)
            val dragonKilled = dragonFightCompound.getByte("DragonKilled", 1)
            val dragonPreviouslyKilled = dragonFightCompound.getByte("PreviouslyKilled", 1)
            val versionCompound = getCompound("Version")
            val versionSnapshot = versionCompound.getByte("Snapshot").asBoolean()
            val versionId = versionCompound.getInt("Id", 2724)
            val versionName = versionCompound.getString("Name", "1.17")
            val dayTime = getLong("DayTime")
            val initialized = getByte("initialized", 1).asBoolean()
            val wasModded = getByte("WasModded").asBoolean()
            val allowCommands = getByte("allowCommands").asBoolean()
            val spawnX = getInt("SpawnX")
            val spawnY = getInt("SpawnY", 70)
            val spawnZ = getInt("SpawnZ")
            val rainTime = getInt("rainTime")
            val thunderTime = getInt("thunderTime")
            val clearWeatherTime = getInt("clearWeatherTime")
            val thundering = getByte("thundering").asBoolean()
            val spawnAngle = getFloat("SpawnAngle", 0.0F)
            val version = getInt("version", 19133)
            val lastPlayed = getLong("LastPlayed", System.currentTimeMillis())
            val dataVersion = getInt("DataVersion", 2724)
        }
        Server.worlds[baseName] = this
    }
    
    companion object {
        const val MAX_WORLD_HEIGHT = 256
        internal val DEFAULT = World(NBTCompound())
    }
}