package cc.essence.core.ui.elements.clickgui

import cc.essence.core.data.Delta
import cc.essence.core.data.alpha
import cc.essence.core.render.skia.Alignment
import cc.essence.core.render.skia.Canvas.paint
import cc.essence.core.render.skia.Canvas.stroke
import cc.essence.core.resource.font.objects.Icons
import cc.essence.core.resource.font.objects.MR
import cc.essence.core.resource.font.objects.Poppins
import cc.essence.core.ui.colors.Colors
import cc.essence.core.ui.elements.Button
import cc.essence.core.ui.elements.Element
import cc.essence.core.ui.elements.clickgui.page.*
import cc.essence.core.ui.gui.ButtonAction
import cc.essence.core.ui.gui.CMD_CLICK_GUI_CLOSE
import cc.essence.core.ui.gui.CMD_CLICK_GUI_OPEN_EDITOR
import cc.essence.core.ui.gui.Screens
import io.github.humbleui.types.RRect
import io.github.humbleui.types.Rect
import kotlin.math.abs
import kotlin.math.sin

class Panel(x: Float, y: Float, w: Float, h: Float) : Element(x, y, w, h) {
    private val elements = mutableListOf<Element>()
    private val pages = mutableListOf<Page>()

    private var currentPage = 0

    init {
        elements.add(Button(MR { close }, x+w-44f-8f,y+8f,44f,44f) {
            Screens.CLICK_GUI.dispatchCMD(CMD_CLICK_GUI_CLOSE)
        }.overrides(font = Icons.materialRound, fontSize = 24f, Button.Style.DARKER))
        elements.add(Button(MR { home }, x+8f,y+64f,44f,44f) {
            currentPage = 0
        }.overrides(font = Icons.materialRound, fontSize = 24f))
        elements.add(Button(MR { grid_view }, x+8f,y+64f+52,44f,44f) {
            currentPage = 1
        }.overrides(font = Icons.materialRound, fontSize = 24f))
        elements.add(Button(MR { settings }, x+8f,y+64f+52*2,44f,44f) {
            currentPage = 2
        }.overrides(font = Icons.materialRound, fontSize = 24f))
        elements.add(Button(MR { desktop_windows }, x+8f,y+64f+52*3,44f,44f) {
            currentPage = 3
        }.overrides(font = Icons.materialRound, fontSize = 24f))
        elements.add(Button(MR { edit }, x+8f,y+64f+52*4,44f,44f) {
            Screens.CLICK_GUI.dispatchCMD(CMD_CLICK_GUI_OPEN_EDITOR)
        }.overrides(font = Icons.materialRound, fontSize = 24f))

        pages.add(PageHome(x+60f,y+68f,w-64f,h-76f))
        pages.add(PageModules(x+60f,y+68f,w-64f,h-76f))
        pages.add(PageSettings(x+60f,y+68f,w-64f,h-76f))
        pages.add(PageScreenshots(x+60f,y+68f,w-64f,h-76f))
    }

    private var smoothIndex = 0f

    override fun render(mouseX: Float, mouseY: Float) {
        if (smoothIndex < currentPage) {
            smoothIndex += (currentPage - smoothIndex) * Delta.time * 10f
        }
        if (smoothIndex > currentPage) {
            smoothIndex -= (smoothIndex - currentPage) * Delta.time * 10f
        }
        if (smoothIndex.isNaN() or smoothIndex.isInfinite()) smoothIndex = currentPage.toFloat()

        ui.round(bounds, 16f, paint(Colors.current.surface()))
        ui.round(x+4f, y+4f, w-8f, 52f, 12f, paint(Colors.current.background()))
        ui.movingTextOverlay("ESSENCE", x+4f, y+4f, w-8f, 52f, 12f, 0.5f, 80f, ui.MVT_DIR_BOTH, 2f)
        ui.round(x+w-44f-8f,y+8f,44f,44f,12f, paint(Colors.current.surface()))

        ui.round(x+w-60f-220f,y+8f,220f,44f,12f, paint(Colors.current.surface()))
        ui.round(x+w-60f-216f,y+12f,212f,36f,8f, paint(Colors.current.background()).stroke(2.5f))
        ui.round(x+w-60f-34f,y+18f,24f,24f,6f, paint(Colors.current.background()).stroke(2f))
        ui.round(x+w-60f-34f,y+18f,24f,18f,7f, paint(Colors.current.background()).stroke(2f))
        ui.text("S", x+w-60f-34f+12f, y+18f+24f/2f-3f, 12f, paint(Colors.current.textSecondary()), Alignment.CENTER_MIDDLE, Poppins.medium)
        ui.text("Search", x+w-60f-216f+12f, y+12f+36f/2f, 16f, paint(Colors.current.textSecondary()), Alignment.LEFT_MIDDLE, Poppins.medium)

        elements.forEach { it.render(mouseX, mouseY) }
        pages.forEachIndexed { index, it ->
            it.openEase.state = currentPage == index
            val f = abs(index - smoothIndex).coerceIn(0f,1f)
            ui.save()
            ui.skia.clipRect(Rect.makeXYWH(x,y,w,60f))
            ui.text(it.name, x+24f - 20f * sin(f), y+12f+36f/2f + (index - smoothIndex) * 60, 24f, paint(Colors.current.text().alpha((1f-f*1.8f).coerceAtLeast(0f))), Alignment.LEFT_MIDDLE, Poppins.bold)
            ui.restore()
            it.y = y+64f + (index - smoothIndex) * (it.h + 60f)
            ui.pushAlpha(1f-f) {
                ui.skia.clipRRect(RRect.makeXYWH(it.x-10f,y+60f-4f,it.w+20f,it.h+8f,8f))
                it.render(mouseX, mouseY)
            }
        }
    }

    override fun mouse(mouseX: Float, mouseY: Float, button: Int, action: ButtonAction) {
        elements.forEach { it.mouse(mouseX, mouseY, button, action) }
        pages[currentPage].mouse(mouseX, mouseY, button, action)
    }

    fun moved(x: Float, y: Float) {
        elements.forEach { it.x += x-this.x; it.y += y-this.y }
        pages.forEach { it.x += x-this.x; it.y += y-this.y }
        this.x = x
        this.y = y
    }
}