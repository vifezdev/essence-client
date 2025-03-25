package cc.essence.core.resource

import java.nio.ByteBuffer

object ResourceCache {
    private val cache = mutableMapOf<String, ByteBuffer>()

    fun push(name: String, data: ByteBuffer) {
        cache[name] = data
    }
}