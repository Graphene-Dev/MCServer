package de.crash.mc.world

import de.crash.mc.nbt.getDouble
import de.crash.mc.nbt.getLong
import me.nullicorn.nedit.type.NBTCompound

class Worldborder internal constructor(nbtCompound: NBTCompound){
    var centerX: Double
    var centerZ: Double
    var borderSize: Double
    var damagePerBlock: Double
    var warningsBlocks: Double
    var warningTime: Double
    var safeZone: Double
    var sizeLerpTarget: Double
    var sizeLerpTime: Long

    init {
        nbtCompound.run {
            centerX = getDouble("BorderCenterX")
            centerZ = getDouble("BorderCenterZ")
            borderSize = getDouble("BorderSize", 5.9999968E7)
            damagePerBlock = getDouble("BorderDamagePerBlock", 0.2)
            warningsBlocks = getDouble("BorderWarningBlocks", 5.0)
            warningTime = getDouble("BorderWarningTime", 15.0)
            safeZone = getDouble("BorderSafeZone", 5.0)
            sizeLerpTarget = getDouble("BorderSizeLerpTarget", 5.9999968E7)
            sizeLerpTime = getLong("BorderSizeLerpTime")
        }
    }
}