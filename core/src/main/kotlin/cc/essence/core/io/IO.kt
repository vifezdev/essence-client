package cc.essence.core.io

import org.lwjgl.system.MemoryUtil
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel

object IO {
    @JvmStatic
    fun resourceToBuffer(path: String, size: Int = 512): ByteBuffer {
        var buffer: ByteBuffer
        val source: InputStream = IO::class.java.getResourceAsStream(path) ?: throw RuntimeException("Failed to load resource: $path")
        val rbc: ReadableByteChannel = source.let { Channels.newChannel(it) }
        buffer = MemoryUtil.memAlloc(size)
        while (true) {
            val bytes = rbc.read(buffer)
            if (bytes == -1) {
                break
            }
            if (buffer.remaining() == 0) {
                val newBuffer= MemoryUtil.memAlloc(buffer.capacity() * 3 / 2)
                buffer.flip()
                newBuffer.put(buffer)
                buffer = newBuffer
            }
        }
        buffer.flip()
        return MemoryUtil.memSlice(buffer)
    }

    @JvmStatic
    fun resourceToString(path: String): String {
        val source: InputStream? = IO::class.java.getResourceAsStream(path)
        return source?.bufferedReader()?.use { it.readText() } ?: throw RuntimeException("Failed to read resource: $path")
    }
}