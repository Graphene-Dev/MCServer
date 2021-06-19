package de.crash.util

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream


object GZip {
    fun decompress(compressedData: ByteArray): ByteArray {
        var result = byteArrayOf()
        try {
            ByteArrayInputStream(compressedData).use { bis ->
                ByteArrayOutputStream().use { bos ->
                    GZIPInputStream(bis).use { gzipIS ->
                        val buffer = ByteArray(1024)
                        var len: Int
                        while (gzipIS.read(buffer).also { len = it } != -1) {
                            bos.write(buffer, 0, len)
                        }
                        result = bos.toByteArray()
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }
    fun compress(uncompressedData: ByteArray): ByteArray {
        var result = byteArrayOf()
        try {
            ByteArrayOutputStream(uncompressedData.size).use { bos ->
                GZIPOutputStream(bos).use { gzipOS ->
                    gzipOS.write(uncompressedData)
                    gzipOS.close()
                    result = bos.toByteArray()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }
}