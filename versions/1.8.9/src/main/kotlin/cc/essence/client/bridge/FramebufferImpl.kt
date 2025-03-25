package cc.essence.client.bridge

import cc.essence.abstraction.client.gl.Framebuffer
import cc.essence.bridge.impl.IFrameBuffer

class FramebufferImpl : IFrameBuffer() {
    override fun new(width: Int, height: Int, depth: Boolean): Framebuffer {
        return net.minecraft.client.gl.Framebuffer(width, height, depth) as Framebuffer
    }
}