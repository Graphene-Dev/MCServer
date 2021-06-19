package de.crash.mc.world.dimension

import de.crash.mc.nbt.asBoolean
import de.crash.mc.nbt.getByte
import de.crash.mc.world.World
import de.crash.mc.world.generator.GeneratorType
import de.crash.util.toMCEnumString
import me.nullicorn.nedit.type.NBTCompound
import java.util.*

class Level internal constructor(nbtCompound: NBTCompound, val world: World){
    val dimensionType: DimensionType
    val generatorSettings: String
    val seed: Long
    val generatorType: GeneratorType
    val biomeType: DimensionBiomeType
    val largeBiomes: Boolean
    init {
        dimensionType = DimensionType.valueOf(nbtCompound.getString("type", "minecraft:overworld").toMCEnumString())
        val dimensionGeneratorCompound = nbtCompound.getCompound("generator")
        generatorSettings = dimensionGeneratorCompound.getString("settings", "minecraft:overworld")
        seed = dimensionGeneratorCompound.getLong("seed", Random().nextLong())
        generatorType = GeneratorType.valueOf(dimensionGeneratorCompound.getString("type", "minecraft:noise").toMCEnumString())
        val dimensionBiomeCompound = dimensionGeneratorCompound.getCompound("biome_source")
        biomeType = DimensionBiomeType.valueOf(dimensionBiomeCompound.getString("type", "minecraft:vanilla_layered").toMCEnumString())
        largeBiomes = dimensionBiomeCompound.getByte("large_biomes").asBoolean()
    }
}