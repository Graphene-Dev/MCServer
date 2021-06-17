package de.crash.mc.world.dimension

internal data class DimensionLevelData(val name: String, val type: String, val generator: DimensionGeneratorData)

internal data class DimensionGeneratorData(val settings: String, val seed: Long, val type: String, val biomeSource: DimensionBiomeSourceData)

internal data class DimensionBiomeSourceData(val seed: Long, val type: String, val largeBiomes: Boolean, val preset: String? = null)
