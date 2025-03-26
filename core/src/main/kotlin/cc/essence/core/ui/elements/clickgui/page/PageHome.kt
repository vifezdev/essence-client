package cc.essence.core.ui.elements.clickgui.page

import cc.essence.core.render.skia.Alignment
import cc.essence.core.ui.gui.ButtonAction

class PageHome(x: Float, y: Float, w: Float, h: Float) : Page("Home", x, y, w, h) {

    override fun render(mouseX: Float, mouseY: Float) {
        val accentColor = 0xFF4A90E2 // Soft blue accent color
        val backgroundColor = 0xFF1E1E1E // Dark background
        val panelColor = 0xFF2C2C2C // Darker panel
        val textColor = 0xFFFFFFFF // White text

        // Background
        ui.rect(x, y, w, h, ui.paint(backgroundColor))

        // Title
        ui.text("Dashboard", x + 20f, y + 25f, 24f, ui.paint(textColor), Alignment.LEFT_MIDDLE)

        // Section Panel (Stylish Container)
        val panelW = w * 0.8f
        val panelH = 140f
        val panelX = x + (w - panelW) / 2f
        val panelY = y + 60f
        ui.round(panelX, panelY, panelW, panelH, 12f, ui.paint(panelColor))

        // Section Header
        ui.text("Quick Access", panelX + 20f, panelY + 20f, 18f, ui.paint(0xFFCCCCCC))

        // Buttons
        val btnW = 140f
        val btnH = 40f
        val btnSpacing = 20f
        val btnY = panelY + 50f

        val btn1X = panelX + (panelW / 2f) - btnW - (btnSpacing / 2f)
        val btn2X = panelX + (panelW / 2f) + (btnSpacing / 2f)

        renderButton(btn1X, btnY, btnW, btnH, "Settings", accentColor)
        renderButton(btn2X, btnY, btnW, btnH, "Mods", accentColor)
    }

    private fun renderButton(x: Float, y: Float, w: Float, h: Float, text: String, color: Long) {
        ui.round(x, y, w, h, 8f, ui.paint(color))
        ui.text(text, x + w / 2f, y + h / 2f, 18f, ui.paint(0xFFFFFFFF), Alignment.CENTER_MIDDLE)
    }

    override fun mouse(mouseX: Float, mouseY: Float, button: Int, action: ButtonAction) {
        // Handle clicks if needed
    }
}