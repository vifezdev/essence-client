package cc.essence.core.gl.vertex

object VertexFormats {
    @JvmField val POS2_UV = FormatBuilder.build(FormatElements.VEC2, FormatElements.UV)
    @JvmField val POS2 = FormatBuilder.build(FormatElements.VEC2)
}