package cc.essence.client.access

import cc.essence.core.gl.Mem
import org.lwjgl.opengl.Display
import org.lwjgl.util.glu.GLU
import java.nio.FloatBuffer
import java.nio.IntBuffer

private typealias pos = FloatArray

object View {
    private val winBuf = Mem.buFloat(4)

    var MODEL_MATRIX: FloatBuffer? = null
    var PROJECTION_MATRIX: FloatBuffer? = null
    var VIEWPORT: IntBuffer? = null

    fun w2s(x: Float, y: Float, z: Float): pos {
        val out = floatArrayOf(0f,0f,0f)

        if (GLU.gluProject(x, y, z, MODEL_MATRIX, PROJECTION_MATRIX, VIEWPORT, winBuf)) {
            out[0] = winBuf[0]
            out[1] = Display.height - winBuf[1]
            out[2] = winBuf[2]
        }
        return out
    }
}