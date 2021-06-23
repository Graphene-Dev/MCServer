package org.graphenedev.netty.packets

import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.util.*
import kotlin.experimental.and
import kotlin.experimental.or


class Packet() {
    constructor(id: Int): this(){
        writeAsVarInt(id)
    }

    constructor(type: PacketType): this(type.id)

    constructor(bytes: ByteArray): this() {
        this.bytes = bytes
    }

    val byteBuffer = mutableListOf<Byte>()
    var bytes: ByteArray = ByteArray(0)
    var readPos = 0

    //region Write Functions
    fun write(value: Byte) {
        byteBuffer.add(value)
    }

    fun write(value: ByteArray) {
        byteBuffer.addAll(value.asList())
    }

    fun write(value: Short) {
        byteBuffer.addAll(value.toByteArray().asList())
    }

    fun writeAsVarInt(value: Int) {
        byteBuffer.addAll(value.toByteArrayAsVarInt())
    }

    fun writeAsVarLong(value: Long) {
        byteBuffer.addAll(value.toByteArrayAsVarLong())
    }

    fun write(value: Int) {
        byteBuffer.addAll(value.toByteArray())
    }

    fun write(value: Long) {
        byteBuffer.addAll(value.toByteArray())
    }

    fun write(value: Float) {
        writeAsVarInt(value.toBits())
    }

    fun write(value: Boolean) {
        write(if(value)1.toByte()else 0.toByte())
    }

    fun write(value: String) {
        val bytes = value.toByteArray().asList()
        writeAsVarInt(bytes.size)
        byteBuffer.addAll(bytes)
    }

    fun write(value: UUID) {
        write(value.toByteArray())
    }
    //endregion

    //region Read Functions
    fun readByte(): Byte {
        if(bytes.size > readPos) {
            val value = bytes[readPos]
            readPos++
            return value
        }else {
            throw CouldNotReadValueOfTypeException("Byte")
        }
    }

    fun readBytes(length: Int): ByteArray {
        if(bytes.size > readPos){
            val value = bytes.copyOfRange(readPos, readPos + length)
            readPos += length
            return value
        }else {
            throw CouldNotReadValueOfTypeException("ByteArray")
        }
    }

    fun readShort(): Short {
        if(bytes.size > readPos){
            val value = (0xff and bytes[readPos].toInt() shl 8 or (0xff and bytes[readPos + 1].toInt()) shl 0).toShort()
            readPos += 2
            return value
        }else {
            throw CouldNotReadValueOfTypeException("Short")
        }
    }

    fun readVarInt(): Int {
        var numRead = 0
        var result = 0
        var read: Byte
        do {
            read = readByte()
            val value: Int = (read and 127).toInt()
            result = result or (value shl 7 * numRead)
            numRead++
            if (numRead > 5) {
                throw RuntimeException("VarInt is too big")
            }
        } while (read and 128.toByte() != 0.toByte())
        return result
    }

    fun readVarLong(): Long {
        var numRead = 0
        var result: Long = 0
        var read: Byte
        do {
            read = readByte()
            val value = (read and 127).toLong()
            result = result or (value shl 7 * numRead)
            numRead++
            if (numRead > 10) {
                throw RuntimeException("VarLong is too big")
            }
        } while (read and 128.toByte() != 0.toByte())
        return result
    }

    fun readFloat(): Float {
        if(bytes.size > readPos) {
            return Float.fromBits(readVarInt())
        }else {
            throw CouldNotReadValueOfTypeException("Float")
        }
    }

    fun readBoolean(): Boolean {
        if(bytes.size > readPos) {
            val value = bytes[readPos].toInt() != 0
            readPos++
            return value
        }else {
            throw CouldNotReadValueOfTypeException("Boolean")
        }
    }

    fun readString(): String {
        if(bytes.size > readPos) {
            val length = readVarInt()
            val value = String(bytes, readPos, length, Charset.defaultCharset())
            readPos += length
            return value
        }else {
            throw CouldNotReadValueOfTypeException("String")
        }
    }

    fun readInt(): Int {
        if(bytes.size > readPos) {
            val result: Int = 0xff and bytes[readPos].toInt() shl 56 or (0xff and bytes[readPos + 1].toInt() shl 48
                    ) or (0xff and bytes[readPos + 2].toInt() shl 40) or (0xff and bytes[readPos + 3].toInt() shl 32)
            readPos += 4
            return result
        }else {
            throw CouldNotReadValueOfTypeException("3ByteInt")
        }
    }

    fun readLong(): Long {
        if(bytes.size > readPos) {
            val result : Long = (0xff and bytes[readPos].toInt()).toLong() shl 56 or ((0xff and bytes[readPos + 1].toInt()).toLong() shl 48
                    ) or ((0xff and bytes[readPos + 2].toInt()).toLong() shl 40
                    ) or ((0xff and bytes[readPos + 3].toInt()).toLong() shl 32
                    ) or ((0xff and bytes[readPos + 4].toInt()).toLong() shl 24
                    ) or ((0xff and bytes[readPos + 5].toInt()).toLong() shl 16
                    ) or ((0xff and bytes[readPos + 6].toInt()).toLong() shl 8) or ((0xff and bytes[readPos + 7].toInt()).toLong() shl 0)
            readPos += 8
            return result
        }else {
            throw CouldNotReadValueOfTypeException("3ByteInt")
        }
    }
    //endregion

    fun getPacketBytes(): ByteArray {
        val lengthBytes = byteBuffer.size.toByteArrayAsVarInt()
        val packetBytes = mutableListOf<Byte>()
        packetBytes.addAll(lengthBytes)
        packetBytes.addAll(byteBuffer)
        return packetBytes.toByteArray()
    }

    fun copy(): Packet{
        val newPacket = Packet(this.bytes)
        newPacket.readPos = this.readPos
        newPacket.byteBuffer.addAll(this.byteBuffer)
        return newPacket
    }
}

class CouldNotReadValueOfTypeException(type: String) : Throwable() {
    override val message: String = "Could not read Value of type $type from packet!"
}

fun Short.toByteArray(): ByteArray = byteArrayOf((this.toInt() ushr 8).toByte(), this.toByte())

fun Int.toByteArrayAsVarInt(): MutableList<Byte> {
    var bvalue = this
    val result = mutableListOf<Byte>()
    do {
        var temp = (bvalue and 127).toByte()
        bvalue = bvalue ushr 7
        if (bvalue != 0) {
            temp = temp or 128.toByte()
        }
        result.add(temp)
    } while (bvalue != 0)
    return result
}

fun Int.toByteArray():  MutableList<Byte> = byteArrayOf(
        (this ushr 24).toByte(), (this ushr 16).toByte(),
        (this ushr 8).toByte(), this.toByte()
    ).toMutableList()

fun Long.toByteArrayAsVarLong(): MutableList<Byte> {
    var bvalue = this
    val result = mutableListOf<Byte>()
    do {
        var temp = (bvalue and 127).toByte()
        bvalue = bvalue ushr 7
        if (bvalue != 0L) {
            temp = temp or 128.toByte()
        }
        result.add(temp)
    } while (bvalue != 0L)
    return result
}

fun Long.toByteArray(): MutableList<Byte> = byteArrayOf(
    (this ushr 56).toByte(), (this ushr 48).toByte(),
    (this ushr 40).toByte(), (this ushr 32).toByte(), (this ushr 24).toByte(),
    (this ushr 16).toByte(), (this ushr 8).toByte(), this.toByte()
).toMutableList()

fun UUID.toByteArray(): ByteArray {
    val bb: ByteBuffer = ByteBuffer.wrap(ByteArray(16))
    bb.putLong(this.mostSignificantBits)
    bb.putLong(this.leastSignificantBits)
    return bb.array()
}

fun getUUIDFromBytes(bytes: ByteArray): UUID {
    val byteBuffer: ByteBuffer = ByteBuffer.wrap(bytes)
    val high: Long = byteBuffer.long
    val low: Long = byteBuffer.long
    return UUID(high, low)
}