package org.graphenedev.mc.world

import me.nullicorn.nedit.type.NBTCompound
import org.graphenedev.mc.nbt.*

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