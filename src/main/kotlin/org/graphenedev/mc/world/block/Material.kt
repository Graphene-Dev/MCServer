package org.graphenedev.mc.world.block

class Material constructor(val value: Int, val transparency: Int) {

    companion object {
        val AIR = Material(0, 1)
        val STONE = Material(1, 0)
        val GRASS = Material(2, 0)
        val DIRT = Material(3, 0)
        val COBBLESTONE = Material(4, 0)
        val PLANKS = Material(5, 0)
    }
}