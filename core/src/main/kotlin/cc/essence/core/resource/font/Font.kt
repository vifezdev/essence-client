package cc.essence.core.resource.font

import cc.essence.core.io.IO
import cc.essence.core.render.nanovg.NVG
import cc.essence.core.resource.Resource
import io.github.humbleui.skija.Data
import io.github.humbleui.skija.Font
import io.github.humbleui.skija.Typeface
import org.lwjgl.system.MemoryUtil
import java.nio.ByteBuffer

open class Font(name: String, path: String) : Resource(name, path) {
    private lateinit var buffer: ByteBuffer
    protected lateinit var data: Data
    protected val sizes = mutableMapOf<Float, Font>()

    override fun load() {
        buffer = IO.resourceToBuffer(path)
        data = Data.makeFromBytes(ByteArray(buffer.remaining()).also { buffer.get(it) })
    }

    open fun bakeSkiaSize(size: Float): Font {
        return sizes.getOrPut(size) {
            Font(Typeface.makeFromData(data), size)
        }
    }

    override fun unload() {
        sizes.values.forEach {
            it.close()
        }
        MemoryUtil.memFree(buffer)
    }
}