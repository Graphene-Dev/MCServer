package de.crash.mc.world

import de.crash.mc.world.dimension.Level
import kotlin.math.ceil

class Location {
    var level: Level
    var x: Double
    var y: Double
    var z: Double
    var yaw: Float
    var pitch: Float
    var blockX: Int
    var blockY: Int
    var blockZ: Int

    constructor(level: Level, x: Double, y: Double, z: Double) : this(level, x, y, z, 0F, 0F)
    constructor(level: Level, x: Double, y: Double, z: Double, yaw: Float, pitch: Float){
        this.level = level
        this.x = x
        this.y = y
        this.z = z
        this.yaw = yaw
        this.pitch = pitch
        this.blockX = ceil(x).toInt()
        this.blockY = ceil(y).toInt()
        this.blockZ = ceil(z).toInt()
    }

    fun add(x: Double, y: Double, z: Double){
        this.x += x
        this.y += y
        this.z += z
    }

    fun add(location: Location) {
        add(location.x, location.y, location.z)
    }
}