package cc.essence.core.feature.module

import cc.essence.core.animation.Ease
import cc.essence.core.bus
import cc.essence.core.data.Bounds
import cc.essence.core.event.EventHUD
import cc.essence.core.render.skia.Canvas

abstract class HUDModule(name: String, id: Short = idCounter++) : Module(name, id) {
    init {
        bus.on<EventHUD> {
            if (enabled) {
                render()
            }
        }
    }

    protected val ui = Canvas

    val hoverEase = Ease { 200f }
    val clickEase = Ease { 200f }

    val bounds = Bounds(0f, 0f, 100f, 100f)

    var relativeX = 0.0
    var relativeY = 0.0

    abstract fun render()
    abstract fun dummy()

    fun updatePosition() {
        bounds.x = (relativeX * (display.width - bounds.width)).toFloat()
        bounds.y = (relativeY * (display.height - bounds.height)).toFloat()
    }
}