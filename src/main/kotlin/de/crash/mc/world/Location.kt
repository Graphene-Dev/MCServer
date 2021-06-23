package de.crash.mc.world

import kotlin.math.ceil

class Location {
    var world: World
    var x: Double
    var y: Double
    var z: Double
    var yaw: Float
    var pitch: Float
    var blockX: Int
    var blockY: Int
    var blockZ: Int

    constructor(world: World, x: Double, y: Double, z: Double) : this(world, x, y, z, 0F, 0F)
    constructor(world: World, x: Double, y: Double, z: Double, yaw: Float, pitch: Float){
        this.world = world
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