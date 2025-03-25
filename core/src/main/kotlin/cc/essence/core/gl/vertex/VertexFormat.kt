package cc.essence.core.gl.vertex

abstract class VertexFormat {
    protected val FLOAT_SIZE = Float.SIZE_BYTES

    abstract val size: Int
    abstract fun setup()
    abstract fun legacyRelease()
}