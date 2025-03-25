package cc.essence.bridge.impl

import cc.essence.abstraction.client.gl.Framebuffer
import cc.essence.bridge.Bridge

@Suppress("INAPPLICABLE_JVM_NAME")
abstract class IFrameBuffer : Bridge {
    @JvmName("newFbo")
    abstract fun new(width: Int, height: Int, depth: Boolean): Framebuffer
}