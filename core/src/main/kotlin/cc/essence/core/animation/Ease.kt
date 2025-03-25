package cc.essence.core.animation

import kotlin.math.min

class Ease(
    private var easing: Easing = Easing.LINEAR,
    private var length: () -> Float,
) {
    private var current: Long = 0
    var state: Boolean = false
        set(value) {
            val lin = linear()
            if (field != value) {
                current =
                    if (value) (System.currentTimeMillis() - (lin * length()).toLong()) else (System.currentTimeMillis() - ((1 - lin) * length()).toLong())
            }
            field = value
        }

    fun forceFinish() {
        current = System.currentTimeMillis() - length().toLong()
        state = true
    }

    fun forceStop() {
        current = System.currentTimeMillis()
        state = false
    }

    fun state(newState: Boolean): Ease {
        state = newState
        return this
    }

    fun defaultState(newState: () -> Boolean): Ease {
        state = newState()
        return this
    }

    fun get(): Float {
        return easing.f(linear().toDouble()).toFloat()
    }

    private fun linear(): Float {
        val t = (System.currentTimeMillis() - current) / length()
        return if (state) clamp(t.toDouble()) else clamp((1f - t).toDouble())
    }

    private fun clamp(d: Double): Float {
        return if (d < 0) 0f else min(d, 1.0).toFloat()
    }
}
