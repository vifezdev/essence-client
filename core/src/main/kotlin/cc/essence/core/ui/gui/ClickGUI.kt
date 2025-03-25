package cc.essence.core.ui.gui

import cc.essence.core.animation.Ease
import cc.essence.core.animation.Easing
import cc.essence.core.render.skia.Canvas
import cc.essence.core.render.nanovg.NVG
import cc.essence.core.resource.image.Images
import cc.essence.core.ui.elements.clickgui.Panel

const val CMD_CLICK_GUI_CLOSE = 0
const val CMD_CLICK_GUI_OPEN_EDITOR = 1

class ClickGUI : EScreen() {
    private var parentScreen: EScreen? = null
    private val panel = Panel(display.width / 2f - 420f, display.height / 2f - 232f, 840f, 464f)

    private var cmd = -1

    fun parent(screen: EScreen) = apply {
        parentScreen = screen
    }

    private val openEase = Ease(Easing.EXPO_IN_OUT) { 400f }

    override fun init() {
        cmd = -1
        openEase.state = true
        panel.moved(display.width / 2f - panel.w/2f, display.height / 2f - panel.h/2f)
    }

    override fun render(mouseX: Float, mouseY: Float, delta: Float) {
        if (!openEase.state && openEase.get() <= 0.01) {
            when (cmd) {
                CMD_CLICK_GUI_CLOSE -> {
                    client.openScreen(parentScreen)
                    parentScreen = null
                }
                CMD_CLICK_GUI_OPEN_EDITOR -> {
                    client.openScreen(Screens.EDITOR.parent(this))
                }
            }
        }

        val (imgX, imgY, imgW, imgH) = Canvas.calcImgSizeAndPos(Images.background_blur)

        Canvas.frame{
            if (client.get().world == null) it.image(imgX, imgY, imgW, imgH, Images.background_blur)
            it.pushAlpha(openEase.get()) {
                it.translate(display.width / 2f, display.height / 2f)
                it.scale(0.8f + openEase.get() * 0.2f, 0.8f + openEase.get() * 0.2f)
                it.translate(-display.width / 2f, -display.height / 2f)
                panel.render(mouseX, mouseY)
            }
        }
    }

    override fun mouse(mouseX: Float, mouseY: Float, button: Int, action: ButtonAction) {
        panel?.mouse(mouseX, mouseY, button, action)
    }

    override fun overrideEscape() = openEase.state

    override fun onEscape() {
        if (cmd == -1) cmd = CMD_CLICK_GUI_CLOSE
        openEase.state = false
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        panel.moved(display.width / 2f - panel.w/2f, display.height / 2f - panel.h/2f)
    }

    fun dispatchCMD(cmd: Int) {
        this.cmd = cmd
        onEscape()
    }
}