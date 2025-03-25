package cc.essence.core.data

import io.github.humbleui.types.Rect

data class Bounds(var x: Float, var y: Float, var width: Float, var height: Float) {
    constructor() : this(0f, 0f, 0f, 0f)

    var left = x
        set(value) {
            field = value
            width = right - left
        }
        get() = x
    var top = y
        set(value) {
            field = value
            height = bottom - top
        }
        get() = y
    var right = x + width
        set(value) {
            field = value
            width = right - left
        }
        get() = x + width
    var bottom = y + height
        set(value) {
            field = value
            height = bottom - top
        }
        get() = y + height
    fun contains(x: Float, y: Float): Boolean {
        return x >= this.x && x <= right && y >= this.y && y <= bottom
    }

    fun intersects(bounds: Bounds): Boolean {
        return left < bounds.right && right > bounds.left && top < bounds.bottom && bottom > bounds.top
    }

    fun expanded(by: Float): Bounds {
        return Bounds(x - by, y - by, width + by * 2, height + by * 2)
    }
}

fun Bounds.toRect(): Rect {
    return Rect.makeXYWH(x, y, width, height)
}

fun Rect.toBounds(): Bounds {
    return Bounds(left, top, right - left, bottom - top)
}