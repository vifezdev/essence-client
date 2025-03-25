package cc.essence.core.gl

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30
import java.nio.FloatBuffer
import java.nio.IntBuffer

object GLZ {
    const val DEPTH_TEST = GL11.GL_DEPTH_TEST
    const val CULL_FACE = GL11.GL_CULL_FACE
    const val BLEND = GL11.GL_BLEND
    const val TEXTURE_2D = GL11.GL_TEXTURE_2D
    const val ALL_ATTRIB_BITS = GL11.GL_ALL_ATTRIB_BITS
    const val ALL_CLIENT_ATTRIB_BITS = GL11.GL_CLIENT_ALL_ATTRIB_BITS
    const val COLOR_BUFFER_BIT = GL11.GL_COLOR_BUFFER_BIT
    const val DEPTH_BUFFER_BIT = GL11.GL_DEPTH_BUFFER_BIT
    const val STENCIL_BUFFER_BIT = GL11.GL_STENCIL_BUFFER_BIT
    const val ALPHA_TEST = GL11.GL_ALPHA_TEST
    const val SCISSOR_TEST = GL11.GL_SCISSOR_TEST
    const val FRAMEBUFFER_BINDING = GL30.GL_FRAMEBUFFER_BINDING
    const val PROJECTION_MATRIX = GL30.GL_PROJECTION_MATRIX
    const val MODEL_VIEW_MATRIX = GL30.GL_MODELVIEW_MATRIX
    const val VIEWPORT = GL30.GL_VIEWPORT

    private var legacy = false

    fun isLegacy() = legacy
    fun setLegacy() {
        legacy = true
    }

    @JvmStatic
    fun enable(cap: Int) = GL11.glEnable(cap)

    @JvmStatic
    fun disable(cap: Int) = GL11.glDisable(cap)

    @JvmStatic
    fun pushAttrib(mask: Int) = GL11.glPushAttrib(mask)

    @JvmStatic
    fun pushClientAttrib(mask: Int) = GL11.glPushClientAttrib(mask)

    @JvmStatic
    fun popAttrib() = GL11.glPopAttrib()

    @JvmStatic
    fun popClientAttrib() = GL11.glPopClientAttrib()

    @JvmStatic
    fun clear(mask: Int) = GL11.glClear(mask)

    @JvmStatic
    fun clearColor(red: Float, green: Float, blue: Float, alpha: Float) = GL11.glClearColor(red, green, blue, alpha)

    @JvmStatic
    fun getInt(pname: Int) = GL11.glGetInteger(pname)

    @JvmStatic
    fun getInt(pname: Int, arr: IntArray) = GL11.glGetIntegerv(pname, arr)

    @JvmStatic
    fun getInt(pname: Int, buf: IntBuffer) = GL11.glGetIntegerv(pname, buf)

    @JvmStatic
    fun getFloat(pname: Int) = GL11.glGetFloat(pname)

    @JvmStatic
    fun getFloat(pname: Int, arr: FloatArray) = GL11.glGetFloatv(pname, arr)

    @JvmStatic
    fun getFloat(pname: Int, buf: FloatBuffer) = GL11.glGetFloatv(pname, buf)
}