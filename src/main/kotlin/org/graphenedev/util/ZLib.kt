package org.graphenedev.util

import java.util.zip.Deflater
import java.util.zip.Inflater

object ZLib {
    fun decompress(compressedData: ByteArray): ByteArray{
        val decompresser = Inflater()
        decompresser.setInput(compressedData)
        while (!decompresser.finished()) {}
        val outputArray = ByteArray(decompresser.totalOut)
        decompresser.inflate(outputArray)
        decompresser.end()
        return outputArray
    }

    fun compress(uncompressedData: ByteArray): ByteArray{
        val compresser = Deflater()
        compresser.setInput(uncompressedData)
        compresser.finish()
        val outputArray = ByteArray(compresser.totalOut)
        compresser.deflate(outputArray)
        compresser.end()
        return outputArray
    }
}