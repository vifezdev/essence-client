package cc.essence.core.feature.module.impl

import cc.essence.core.data.alpha
import cc.essence.core.feature.module.HUDModule
import cc.essence.core.render.skia.Alignment
import cc.essence.core.resource.font.objects.Poppins
import cc.essence.core.ui.colors.Colors

@Suppress("unused", "ktPropBy")
abstract class TextModule(name: String, id: Short = idCounter++) : HUDModule(name, id) {
    protected val staticSize = floatArrayOf(140f, 40f)

    private val _grSize = group("Size")
    private val setStaticSize by boolean(_grSize, "Static Size", true)
    private val setExpandWidth by float(_grSize, "Width", 15f, 10f..30f).visibleIf { !setStaticSize }
    private val setExpandHeight by float(_grSize, "Height", 15f, 10f..30f).visibleIf { !setStaticSize }
    private val setStaticWidth by float(_grSize, "Width", staticSize[0], staticSize[0]..staticSize[0] + 120f).visibleIf { !setStaticSize }
    private val setStaticHeight by float(_grSize, "Height", staticSize[1], staticSize[1]..staticSize[1] + 40f).visibleIf { !setStaticSize }

    private val _grStyle = group("Style")
    private val setDrawShadow by boolean(_grStyle, "Draw Shadow", true)

    private fun updateSize(dummy: Boolean) {
        val b = ui.textRect(getText(dummy), 18f, Poppins.regular)
        if (setStaticSize) {
            bounds.width = setStaticWidth.value
            bounds.height = setStaticHeight.value
            return
        }
        bounds.width = setExpandWidth.value * 2f + b.width
        bounds.height = setExpandHeight.value * 2f + b.height
    }

    override fun render() {
        updateSize(false)
        if (setDrawShadow) ui.shadow(bounds, 8f, 4f, 10f, Colors.current.surface().alpha(.4f))
        ui.round(bounds, 8f, ui.paint(Colors.current.background()))
        ui.text(
            getText(false),
            bounds.x + bounds.width / 2f,
            bounds.y + bounds.height / 2f,
            18f,
            ui.paint(Colors.current.text()),
            align = Alignment.CENTER_MIDDLE
        )
    }

    override fun dummy() {
        updateSize(true)
        if (setDrawShadow) ui.shadow(bounds, 8f, 4f, 10f, Colors.current.surface().alpha(.4f))
        ui.round(bounds, 8f, ui.paint(Colors.current.background()))
        ui.text(
            getText(true),
            bounds.x + bounds.width / 2f,
            bounds.y + bounds.height / 2f,
            18f,
            ui.paint(Colors.current.text()),
            align = Alignment.CENTER_MIDDLE
        )
    }

    abstract fun getText(dummy: Boolean): String
}