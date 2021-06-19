package de.crash.mc.world

import de.crash.mc.nbt.asBoolean
import de.crash.mc.nbt.getByte
import de.crash.mc.nbt.getInt
import me.nullicorn.nedit.type.NBTCompound

class Weather internal constructor(nbtCompound: NBTCompound){
    var raining: Boolean
    var rainTime: Int
    var thunderTime: Int
    var clearWeatherTime: Int
    var thundering: Boolean

    init {
        nbtCompound.run {
            raining = getByte("raining").asBoolean()
            rainTime = getInt("rainTime")
            thunderTime = getInt("thunderTime")
            clearWeatherTime = getInt("clearWeatherTime")
            thundering = getByte("thundering").asBoolean()
        }
    }
}