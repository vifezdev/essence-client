package cc.essence.core.gl.vertex

import org.lwjgl.opengl.GL30.*
import java.nio.ByteBuffer
import java.nio.ByteOrder

class VertexData(private val format: VertexFormat) {
    private var id: Int = 0

    fun create() {
        id = glGenBuffers()
    }

    fun bind() = glBindBuffer(GL_ARRAY_BUFFER, id)

    fun unbind() = glBindBuffer(GL_ARRAY_BUFFER, 0)

    fun delete() = glDeleteBuffers(id)

    fun upload(data: FloatArray) {
        bind()
        val byteByffer = ByteBuffer.allocateDirect(data.size * Float.SIZE_BYTES)
        byteByffer.order(ByteOrder.nativeOrder())

        val buffer = byteByffer.asFloatBuffer()
        buffer.put(data)
        buffer.flip()

        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        unbind()
    }

    fun render() {
        bind()
        format.setup()
        glDrawArrays(GL_TRIANGLES, 0, 3)
        format.legacyRelease()
        unbind()
    }
}