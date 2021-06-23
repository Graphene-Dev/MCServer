package org.graphenedev.mc.nbt

import me.nullicorn.nedit.type.NBTCompound

fun NBTCompound.getInt(key: String): Int = this.getInt(key, 0)
fun NBTCompound.getByte(key: String): Byte = this.getByte(key, 0)
fun NBTCompound.getDouble(key: String): Double = this.getDouble(key, 0.0)
fun NBTCompound.getLong(key: String): Long = this.getLong(key, 0)
fun NBTCompound.getFloat(key: String): Float = this.getFloat(key, 0.0F)

fun Byte.asBoolean(): Boolean = this == 1.toByte()