package cc.essence.core.ui.elements.clickgui.page

import cc.essence.core.feature.module.ModuleManager
import cc.essence.core.ui.elements.clickgui.module.ModuleElement
import cc.essence.core.ui.gui.ButtonAction

class PageModules(x: Float, y: Float, w: Float, h: Float) : Page("Modules", x, y, w, h) {
    private val modules = ModuleManager.modules.map { ModuleElement(it.value, 0f, 0f, 186f, 110f) }

    override fun render(mouseX: Float, mouseY: Float) {
        var mx = 0f
        var my = 0f
        val mw = 186f
        val mh = 130f

        val modulesPerRow = (w / mw).toInt()
        val modulesGap = (w - mw * modulesPerRow) / (modulesPerRow-1)

//        ui.save()
//        ui.skia.clipRRect(RRect.makeXYWH(x,y,w-modulesGap,h,16f))
        modules.forEach {
            it.x = x + mx * (mw + modulesGap)
            it.y = y + my * (mh + 8)
            it.w = mw
            it.h = mh
            it.render(mouseX, mouseY)
            mx++
            if (mx >= modulesPerRow) {
                mx = 0f
                my++
            }
        }
//        for (i in 0 until 9) {
//            val modX = x+mx*(mw + modulesGap)
//            val modY = y+my*(mh + 8)
//
//            val btnClr = ui.paint(if (i % 3 == 0) Colors.current.primary() else Colors.current.textSecondary().interpolate(Colors.current.background(), 0.5f))
//
//            ui.round(modX, modY, mw, mh, 8f, ui.paint(Colors.current.background()))
//            ui.round(modX, modY + mh - 30f, mw, 30f, 8f, btnClr)
//            ui.rect(modX, modY + mh - 30f, mw, 10f, btnClr)
//            ui.text(if (i % 3 == 0) "Enabled" else "Disabled", modX + mw / 2f, modY + mh - 15f, 18f, ui.paint(Colors.current.text()), Alignment.CENTER_MIDDLE)
//            ui.text(MR { punch_clock }, modX + mw / 2f, modY + (mh - 30f) / 2f, 46f, ui.paint(Colors.current.text()), Alignment.CENTER_MIDDLE, Icons.materialRound)
//            mx++
//            if (mx >= modulesPerRow) {
//                mx = 0f
//                my++
//            }
//        }
//        ui.restore()
    }

    override fun mouse(mouseX: Float, mouseY: Float, button: Int, action: ButtonAction) {
        modules.forEach { it.mouse(mouseX, mouseY, button, action) }
    }
}