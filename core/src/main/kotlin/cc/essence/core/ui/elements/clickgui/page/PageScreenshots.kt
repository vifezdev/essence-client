package cc.essence.core.ui.elements.clickgui.page

import cc.essence.core.ui.gui.ButtonAction

class PageScreenshots(x: Float, y: Float, w: Float, h: Float) : Page("Screenshots", x, y, w, h) {
    override fun render(mouseX: Float, mouseY: Float) {
        ui.rect(bounds, ui.paint(0xFF262626))
    }

    override fun mouse(mouseX: Float, mouseY: Float, button: Int, action: ButtonAction) {

    }
}