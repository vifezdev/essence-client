package cc.essence.core.ui.elements

import cc.essence.core.animation.Ease
import cc.essence.core.animation.Easing
import cc.essence.core.data.alpha
import cc.essence.core.data.interpolate
import cc.essence.core.gl.Cursor
import cc.essence.core.gl.Cursor.set
import cc.essence.core.render.skia.Alignment
import cc.essence.core.render.skia.Canvas.paint
import cc.essence.core.render.skia.Canvas.stroke
import cc.essence.core.resource.font.Font
import cc.essence.core.resource.font.objects.Poppins
import cc.essence.core.ui.colors.Colors
import cc.essence.core.ui.gui.ButtonAction

class Button(private val text: String, x: Float, y: Float, w: Float, h: Float, private val action: () -> Unit) :
    Element(x, y, w, h) {
    private val hoverEase = Ease(Easing.QUAD_OUT) { 100f }
    private val clickEase = Ease { 1000f }
    private val overlayEase = Ease(Easing.QUAD_OUT) { 300f }

    private var style = Style.DEFAULT
    private var font = Poppins.regular
    private var fontSize = 18f
    private var hoverOverlay = false

    fun overrides(font: Font = Poppins.regular, fontSize: Float = 18f, style: Style = Style.DEFAULT, hoverOverlay: Boolean = false): Button {
        this.font = font
        this.fontSize = fontSize
        this.style = style
        this.hoverOverlay = hoverOverlay
        return this
    }

    override fun render(mouseX: Float, mouseY: Float) {
        if (hoverEase.state) {
            set(Cursor.POINTING_HAND)
        }

        val background = when (style) {
            Style.DEFAULT -> Colors.current.background()
            Style.PRIMARY -> Colors.current.primary()
            Style.DARKER -> Colors.current.surface()
        }

        hoverEase.state = bounds.contains(mouseX, mouseY)
        overlayEase.state = hoverEase.state


        ui.round(bounds, 8f, paint(background))
        ui.round(bounds.expanded(5f), 14f, paint(Colors.current.text().alpha(hoverEase.get() * 0.8f)).stroke(3f * hoverEase.get()))
        if (hoverOverlay) {
            ui.movingTextOverlay(text.uppercase(), bounds.x, bounds.y, bounds.width, bounds.height, 8f, overlayEase.get())
        }
        ui.round(bounds, 8f, paint(0.interpolate(Colors.current.text().alpha(0.2f), (clickEase.get()))))

        ui.text(text, x + w / 2f, y + h / 2f + 1, fontSize, paint(Colors.current.text()), Alignment.CENTER_MIDDLE, font)
    }

    override fun mouse(mouseX: Float, mouseY: Float, button: Int, action: ButtonAction) {
        if (bounds.contains(mouseX, mouseY)) {
            when (action) {
                ButtonAction.PRESS -> {
                    clickEase.state = true
                    clickEase.forceFinish()
                }
                ButtonAction.RELEASE -> {
                    if (clickEase.state) {
                        action()
                    }
                    clickEase.state = false
                }
            }
        } else {
            clickEase.state = false
        }
    }

    enum class Style {
        DEFAULT,
        DARKER,
        PRIMARY,
    }
}