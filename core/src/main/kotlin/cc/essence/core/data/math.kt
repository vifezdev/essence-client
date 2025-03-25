package cc.essence.core.data

fun Number.interpolate(other: Number, progress: Float): Int {
    val th = this.toLong()
    val oh = other.toLong()

    val a = (th shr 24 and 0xff) * (1 - progress) + (oh shr 24 and 0xff) * progress
    val r = (th shr 16 and 0xff) * (1 - progress) + (oh shr 16 and 0xff) * progress
    val g = (th shr 8 and 0xff) * (1 - progress) + (oh shr 8 and 0xff) * progress
    val b = (th and 0xff) * (1 - progress) + (oh and 0xff) * progress
    return (a.toInt() shl 24 and 0xff000000.toInt()) or
            (r.toInt() shl 16 and 0x00ff0000) or
            (g.toInt() shl 8 and 0x0000ff00) or
            (b.toInt() and 0x000000ff)
}

fun Float.interpolate(other: Float, progress: Float): Float {
    return this * (1 - progress) + other * progress
}

fun Number.toComponents(length: Int): FloatArray {
    val num = this.toLong()

    var counter = 8 * length - 8

    val components = FloatArray(length)
    for (i in 0 until length) {
        components[i] = ((num shr counter and 0xff) / 255f)
        counter -= 8
    }

    return components
}

fun Number.alpha(alpha: Float): Int {
    val num = this.toLong()

    val a = (255 * alpha).toInt()
    val r = (num shr 16 and 0xff)
    val g = (num shr 8 and 0xff)
    val b = (num and 0xff)

    return (a shl 24 and 0xff000000.toInt()) or
            (r.toInt() shl 16 and 0x00ff0000) or
            (g.toInt() shl 8 and 0x0000ff00) or
            (b.toInt() and 0x000000ff)
}