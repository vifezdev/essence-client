package cc.essence.core.ui.elements

import cc.essence.core.data.Bounds
import cc.essence.core.render.skia.Canvas
import cc.essence.core.ui.gui.ButtonAction

abstract class Element(var x: Float, var y: Float, var w: Float, var h: Float) {
    protected val ui = Canvas

    val bounds: Bounds
        get() = Bounds(x, y, w, h)

    abstract fun render(mouseX: Float, mouseY: Float)
    abstract fun mouse(mouseX: Float, mouseY: Float, button: Int, action: ButtonAction)
}