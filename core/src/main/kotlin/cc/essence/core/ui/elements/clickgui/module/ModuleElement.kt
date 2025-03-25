package cc.essence.core.ui.elements.clickgui.module

import cc.essence.core.animation.Ease
import cc.essence.core.animation.Easing
import cc.essence.core.data.Bounds
import cc.essence.core.data.alpha
import cc.essence.core.data.interpolate
import cc.essence.core.feature.module.Module
import cc.essence.core.gl.Cursor
import cc.essence.core.render.skia.Alignment
import cc.essence.core.render.skia.Canvas.paint
import cc.essence.core.render.skia.Canvas.stroke
import cc.essence.core.resource.font.objects.Icons
import cc.essence.core.resource.font.objects.MR
import cc.essence.core.ui.colors.Colors
import cc.essence.core.ui.elements.Element
import cc.essence.core.ui.gui.ButtonAction
import io.github.humbleui.types.Rect
import kotlin.math.abs

class ModuleElement(val module: Module, x: Float, y: Float, w: Float, h: Float) : Element(x, y, w, h) {
    private val toggleEase = Ease(Easing.EXPO_IN_OUT) { 200f }.defaultState { module.enabled }
    private val modHoverEase = Ease(Easing.QUAD_OUT) { 100f }
    private val hoverEase = Ease { 140f }
    private val clickEase = Ease { 1000f }

    override fun render(mouseX: Float, mouseY: Float) {
        modHoverEase.state = Bounds(x, y+h-30f, w, 30f).contains(mouseX, mouseY)
        toggleEase.state = module.enabled
        hoverEase.state = bounds.contains(mouseX, mouseY)

        if (modHoverEase.state) {
            Cursor.set(Cursor.POINTING_HAND)
        }

        val btnClr = ui.paint(
            Colors.current.primary().interpolate(
                Colors.current.textSecondary().interpolate(
                    Colors.current.background(), 0.5f
                ), 1f - toggleEase.get()
            )
        )

        ui.round(x, y, w, h, 8f, ui.paint(Colors.current.background()))
        ui.round(x, y + h - 30f, w, 30f, 8f, btnClr)
        ui.rect(x, y + h - 30f, w, 10f, btnClr)
        ui.save()
        ui.skia.clipRect(Rect.makeXYWH(x,y+h-30f,w,30f))
        ui.text(
            module.name,
            x + w / 2f,
            y + h - 15f,
            18f,
            ui.paint(Colors.current.text()),
            Alignment.CENTER_MIDDLE
        )
        ui.restore()
        ui.text(
            MR { punch_clock },
            x + w / 2f,
            y + (h - 30f) / 2f,
            46f,
            ui.paint(Colors.current.text()),
            Alignment.CENTER_MIDDLE,
            Icons.materialRound
        )
        ui.round(bounds.expanded(4f), 12f, paint(Colors.current.text().alpha(hoverEase.get())).stroke(2.4f * hoverEase.get()))
    }

    override fun mouse(mouseX: Float, mouseY: Float, button: Int, action: ButtonAction) {
        if (modHoverEase.state) {
            when (action) {
                ButtonAction.PRESS -> {
                    clickEase.state = true
                    clickEase.forceFinish()
                }

                ButtonAction.RELEASE -> {
                    if (clickEase.state) {
                        if (modHoverEase.state) {
                            module.enabled = !module.enabled
                        }
                    }
                    clickEase.state = false
                }
            }
        } else {
            clickEase.state = false
        }
    }
}