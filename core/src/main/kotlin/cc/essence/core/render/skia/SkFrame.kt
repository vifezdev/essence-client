package cc.essence.core.render.skia

import cc.essence.abstraction.client.gl.Framebuffer
import cc.essence.bridge.BridgeRegistry
import cc.essence.core.gl.GLZ
import cc.essence.core.gl.StateSaver
import cc.essence.core.render.skia.Canvas
import cc.essence.core.util.Invoker
import io.github.humbleui.skija.*
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL41

object SkFrame {
    private val context by lazy { DirectContext.makeGL() }
    private var surface: Surface? = null
    private var renderTarget: BackendRenderTarget? = null
    private lateinit var fbo: Framebuffer
    private val display = BridgeRegistry.display()

    @JvmStatic
    fun create(width: Int, height: Int) {
        fbo = BridgeRegistry.client().get().mainFbo()
        renderTarget = BackendRenderTarget.makeGL(width, height, 0, 8, fbo.id, FramebufferFormat.GR_GL_RGBA8)
        surface = Surface.wrapBackendRenderTarget(context, renderTarget!!, SurfaceOrigin.BOTTOM_LEFT, SurfaceColorFormat.RGBA_8888, ColorSpace.getSRGB(), SurfaceProps(true, PixelGeometry.RGB_H))
    }

    @JvmStatic
    fun destroy() {
        surface?.close()
        renderTarget?.close()
        context.close()
    }

    @JvmStatic
    fun handleResize(width: Int, height: Int) {
        if (fbo.id != BridgeRegistry.client().get().mainFbo().id) fbo.resize(width, height)
        renderTarget?.close()
        renderTarget = BackendRenderTarget.makeGL(width, height, 0, 8, fbo.id, FramebufferFormat.GR_GL_RGBA8)
        surface?.close()
        surface = Surface.wrapBackendRenderTarget(context, renderTarget!!, SurfaceOrigin.BOTTOM_LEFT, SurfaceColorFormat.RGBA_8888, ColorSpace.getSRGB(), SurfaceProps(true, PixelGeometry.RGB_H))
    }

    private var outputFb = 0

    @JvmStatic
    fun beginFrame() {
        outputFb = GL30.glGetInteger(GL30.GL_FRAMEBUFFER_BINDING)
        StateSaver.backup()
        context.resetGLAll()
        Canvas.skia = surface?.canvas ?: error("Skia canvas is null")
        GLZ.disable(GLZ.ALPHA_TEST)
    }

    @JvmStatic
    fun endFrame() {
        context.flush()
        GLZ.enable(GLZ.ALPHA_TEST)
        StateSaver.restore()
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, outputFb)
    }

    @JvmStatic
    fun render() {
        if (fbo.id == BridgeRegistry.client().get().mainFbo().id) return

        GLZ.enable(GLZ.BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GLZ.disable(GLZ.ALPHA_TEST)
        fbo.draw(display.width, display.height)
        GLZ.enable(GLZ.ALPHA_TEST)
        GLZ.disable(GLZ.BLEND)
    }
}