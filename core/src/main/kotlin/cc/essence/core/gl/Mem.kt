package cc.essence.core.gl

import org.lwjgl.BufferUtils
import org.lwjgl.system.MemoryStack
import java.nio.FloatBuffer

object Mem {
    var currentStack: MemoryStack? = null

    fun pushStack() {
        currentStack = MemoryStack.stackPush()
    }

    fun popStack() {
        currentStack?.pop()
        currentStack = null
    }

    fun use(stack: Mem.() -> Unit) {
        pushStack()
        stack(this)
        popStack()
    }

    fun mallocFloat(size: Int): FloatBuffer {
        return currentStack?.mallocFloat(size) ?: error("No current MemoryStack. Make sure to call `Mem#pushStack()` before calling `mallocX`!")
    }

    fun buFloat(size: Int) = BufferUtils.createFloatBuffer(size)
    fun buInt(size: Int) = BufferUtils.createIntBuffer(size)
}