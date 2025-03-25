package cc.essence.core.gl.vertex

import cc.essence.core.gl.GLZ
import org.lwjgl.opengl.GL20.*

class FormatBuilder {
    companion object {
        fun build(vararg enumElements: FormatElements): VertexFormat {
            val legacy = GLZ.isLegacy()

            val elements = enumElements.map { it.element }

            val format = object : VertexFormat() {
                override val size: Int
                    get() = elements.sumOf { it.size } * FLOAT_SIZE

                override fun setup() {
                    var offset = 0L
                    elements.forEachIndexed { index, element ->
                        if (legacy) {
                            when (enumElements[index]) {
                                FormatElements.VEC2, FormatElements.VEC3, FormatElements.VEC4 -> {
                                    glEnableClientState(GL_VERTEX_ARRAY)
                                    glVertexPointer(element.size, element.type, size, offset * FLOAT_SIZE)
                                }
                                FormatElements.COLOR -> {
                                    glEnableClientState(GL_COLOR_ARRAY)
                                    glColorPointer(element.size, element.type, size, offset * FLOAT_SIZE)
                                }
                                FormatElements.NORMAL -> {
                                    glEnableClientState(GL_NORMAL_ARRAY)
                                    glNormalPointer(element.type, size, offset * FLOAT_SIZE)
                                }
                                FormatElements.UV -> {
                                    glEnableClientState(GL_TEXTURE_COORD_ARRAY)
                                    glTexCoordPointer(element.size, element.type, size, offset * FLOAT_SIZE)
                                }
                            }
                        } else {
                            glVertexAttribPointer(index, element.size, element.type, false, size, offset * FLOAT_SIZE)
                            glEnableVertexAttribArray(index)
                        }
                        offset += element.size
                    }
                }

                override fun legacyRelease() {
                    elements.forEachIndexed { index, _ ->
                        if (legacy) {
                            when (enumElements[index]) {
                                FormatElements.VEC2, FormatElements.VEC3, FormatElements.VEC4 -> {
                                    glDisableClientState(GL_VERTEX_ARRAY)
                                }
                                FormatElements.COLOR -> {
                                    glDisableClientState(GL_COLOR_ARRAY)
                                }
                                FormatElements.NORMAL -> {
                                    glDisableClientState(GL_NORMAL_ARRAY)
                                }
                                FormatElements.UV -> {
                                    glDisableClientState(GL_TEXTURE_COORD_ARRAY)
                                }
                            }
                        }
                    }
                }
            }
            return format
        }
    }
}

class FormatElement(val type: Int, val size: Int)

enum class FormatElements(val element: FormatElement) {
    VEC2(FormatElement(GL_FLOAT, 2)),
    VEC3(FormatElement(GL_FLOAT, 3)),
    VEC4(FormatElement(GL_FLOAT, 4)),
    COLOR(FormatElement(GL_FLOAT, 4)),
    NORMAL(FormatElement(GL_FLOAT, 2)),
    UV(FormatElement(GL_FLOAT, 2))
}