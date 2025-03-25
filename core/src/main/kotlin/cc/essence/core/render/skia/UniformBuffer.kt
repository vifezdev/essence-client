package cc.essence.core.render.skia

import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer

class UniformBuffer {
    private val floats = mutableListOf<Any>()

    companion object {
        fun build(func: UniformBuffer.() -> Unit): ByteBuffer {
            MemoryStack.stackPush().use { return UniformBuffer().apply(func).build(it) }
        }
    }

    fun float(value: Float) {
        floats.add(value)
    }

    fun int(value: Int) {
        floats.add(value)
    }

    fun vec2(x: Float, y: Float) {
        floats.add(x)
        floats.add(y)
    }

    fun vec3(x: Float, y: Float, z: Float) {
        floats.add(x)
        floats.add(y)
        floats.add(z)
    }

    fun vec4(x: Float, y: Float, z: Float, w: Float) {
        floats.add(x)
        floats.add(y)
        floats.add(z)
        floats.add(w)
    }

    fun build(stack: MemoryStack): ByteBuffer {
        val buffer = stack.malloc(floats.size * 4)
        floats.forEach {
            when (it) {
                is Float -> buffer.putFloat(it)
                is Int -> buffer.putInt(it)
                else -> throw IllegalArgumentException("Invalid type")
            }
        }
        return buffer.flip()
    }
}