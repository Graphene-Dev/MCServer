package de.crash.netty.packets

import java.nio.charset.Charset
import java.util.*
import kotlin.experimental.and
import kotlin.experimental.or


class Packet() {
    constructor(id: Int): this(){
        write(id)
    }

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
        byteBuffer.addAll(value.getBytes().asList())
    }

    fun write(value: Int) {
        var bvalue = value
        do {
            var temp = (bvalue and 127).toByte()
            bvalue = bvalue ushr 7
            if (bvalue != 0) {
                temp = temp or 128.toByte()
            }
            write(temp)
        } while (bvalue != 0)
    }

    fun write(value: Long) {
        var bvalue = value
        do {
            var temp = (bvalue and 127).toByte()
            bvalue = bvalue ushr 7
            if (bvalue != 0L) {
                temp = temp or 128.toByte()
            }
            write(temp)
        } while (bvalue != 0L)
    }

    fun write(value: Float) {
        write(value.toBits())
    }

    fun write(value: Boolean) {
        write(if(value)1.toByte()else 0.toByte())
    }

    fun write(value: String) {
        val bytes = value.toByteArray().asList()
        write(bytes.size)
        byteBuffer.addAll(bytes)
    }

    fun write(value: UUID) {
        write(value.mostSignificantBits)
        write(value.leastSignificantBits)
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
    //endregion

    fun getPacketBytes(): ByteArray {
        val lengthBytes = byteBuffer.size.getBytes()
        val packetBytes = mutableListOf<Byte>()
        packetBytes.addAll(lengthBytes)
        packetBytes.addAll(byteBuffer)
        return packetBytes.toByteArray()
    }
}

class CouldNotReadValueOfTypeException(type: String) : Throwable() {
    override val message: String = "Could not read Value of type $type from packet!"
}

fun Short.getBytes(): ByteArray = byteArrayOf((this.toInt() ushr 8).toByte(), this.toByte())

fun Int.getBytes(): MutableList<Byte> {
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