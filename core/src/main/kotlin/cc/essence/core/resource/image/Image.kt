package cc.essence.core.resource.image

import cc.essence.core.io.IO
import cc.essence.core.resource.Resource
import io.github.humbleui.skija.ColorAlphaType
import io.github.humbleui.skija.ColorSpace
import io.github.humbleui.skija.ColorType
import io.github.humbleui.skija.Data
import io.github.humbleui.skija.Image
import io.github.humbleui.skija.ImageInfo
import org.lwjgl.stb.STBImage
import java.nio.ByteBuffer

class Image(name: String, path: String, private val repeating: Boolean = false) : Resource(name, path) {
    private lateinit var data: ByteBuffer
    private lateinit var skImage: Image
    var width: Int = 0
        private set
    var height: Int = 0
        private set

    override fun load() {
        data = IO.resourceToBuffer(path)
        val w = intArrayOf(0)
        val h = intArrayOf(0)
        val c = intArrayOf(0)

        STBImage.stbi_load_from_memory(data, w, h, c, 4)!!

        width = w[0]
        height = h[0]

        skImage = Image.makeDeferredFromEncodedBytes(ByteArray(data.remaining()).also { data.get(it) })
    }

    override fun unload() {
        skImage.close()
        data.clear()
    }

    fun get() = skImage
}