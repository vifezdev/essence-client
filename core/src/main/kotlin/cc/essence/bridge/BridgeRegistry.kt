@file:Suppress("UNCHECKED_CAST")

package cc.essence.bridge

import cc.essence.bridge.impl.IClient
import cc.essence.bridge.impl.IDisplay
import cc.essence.bridge.impl.IFrameBuffer
import cc.essence.bridge.impl.IInput

object BridgeRegistry {
    private var bridges = emptyArray<Bridge>()

    @JvmStatic
    fun <T : Bridge> register(bridge: T) {
        bridges += bridge
    }

    @JvmStatic
    @JvmName("get")
    fun <T : Bridge> javaGet(clazz: Class<T>): T {
        return bridges.first { clazz.isInstance(it) } as T
    }

    inline fun <reified T : Bridge> get(): T = javaGet(T::class.java)
    inline fun <reified T : Bridge> register() {
        register(T::class.java.constructors.first().newInstance() as T)
    }

    @JvmStatic fun display(): IDisplay = get()
    @JvmStatic fun client(): IClient = get()
    @JvmStatic fun fbo(): IFrameBuffer = get()
    @JvmStatic fun input(): IInput = get()
}