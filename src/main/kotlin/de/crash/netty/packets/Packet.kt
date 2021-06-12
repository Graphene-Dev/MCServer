package de.crash.netty.packets

import java.nio.charset.Charset


class Packet() {
    constructor(id: Int): this(){
        write(id)
    }

    constructor(bytes: ByteArray): this() {
        this.bytes = bytes
    }

    val byteBuffer = mutableListOf<Byte>()
    private var bytes: ByteArray = ByteArray(0)
    private var readPos = 0

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
        byteBuffer.addAll(value.getBytes().asList())
    }

    fun write(value: Long) {
        byteBuffer.addAll(value.getBytes().asList())
    }

    fun write(value: Float) {
        write(value.toBits())
    }

    fun write(value: Boolean) {
        write(if(value)1.toByte()else 0.toByte())
    }

    fun write(value: String) {
        byteBuffer.addAll(value.toByteArray().asList())
    }
    //endregion

    //region Read Functions
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

    fun readInt(): Int {
        if(bytes.size > readPos){
            val value = ((0xff and bytes[readPos].toInt()) shl 56 or ((0xff and bytes[readPos + 1].toInt()) shl 48
                    ) or ((0xff and bytes[readPos + 2].toInt()) shl 40) or ((0xff and bytes[readPos + 3].toInt()) shl 32))
            readPos += 4
            return value
        }else {
            throw CouldNotReadValueOfTypeException("Int")
        }
    }

    fun readLong(): Long {
        if(bytes.size > readPos){
            val value =  ((0xff and bytes[readPos].toInt()).toLong() shl 56 or ((0xff and bytes[readPos + 1].toInt()).toLong() shl 48
                    ) or ((0xff and bytes[readPos + 2].toInt()).toLong() shl 40
                    ) or ((0xff and bytes[readPos + 3].toInt()).toLong() shl 32
                    ) or ((0xff and bytes[readPos + 4].toInt()).toLong() shl 24
                    ) or ((0xff and bytes[readPos + 5].toInt()).toLong() shl 16
                    ) or ((0xff and bytes[readPos + 6].toInt()).toLong() shl 8) or ((0xff and bytes[readPos + 7].toInt()).toLong() shl 0))
            readPos += 8
            return value
        }else {
            throw CouldNotReadValueOfTypeException("Long")
        }
    }

    fun readFloat(): Float {
        if(bytes.size > readPos) {
            return Float.fromBits(readInt())
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
            val length = readInt()
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
        packetBytes.addAll(lengthBytes.asList())
        packetBytes.addAll(byteBuffer)
        return packetBytes.toByteArray()
    }
}

class CouldNotReadValueOfTypeException(type: String) : Throwable() {
    override val message: String = "Could not read Value of type $type from packet!"
}

fun Short.getBytes(): ByteArray = byteArrayOf((this.toInt() ushr 8).toByte(), this.toByte())

fun Int.getBytes(): ByteArray = byteArrayOf(
    (this ushr 24).toByte(), (this ushr 16).toByte(),
    (this ushr 8).toByte(), this.toByte()
)

fun Long.getBytes() = byteArrayOf(
    (this ushr 56).toByte(), (this ushr 48).toByte(),
    (this ushr 40).toByte(), (this ushr 32).toByte(), (this ushr 24).toByte(),
    (this ushr 16).toByte(), (this ushr 8).toByte(), this.toByte()
)