package cc.essence.core.ui.gui

import cc.essence.core.animation.Ease
import cc.essence.core.animation.Easing
import cc.essence.core.data.alpha
import cc.essence.core.feature.module.HUDModule
import cc.essence.core.feature.module.ModuleManager
import cc.essence.core.gl.Cursor
import cc.essence.core.render.skia.Canvas
import cc.essence.core.render.skia.Canvas.paint
import cc.essence.core.render.skia.Canvas.stroke
import cc.essence.core.resource.font.objects.Poppins
import cc.essence.core.resource.image.Images
import cc.essence.core.ui.colors.Colors
import cc.essence.core.ui.elements.Button

class Editor : EScreen() {
    private var parentScreen: EScreen? = null

    private val openEase = Ease(Easing.EXPO_IN_OUT) { 400f }

    private var cmd = -1
    private val backButton = Button("Back", 0f, 0f, 240f, 50f) {
        openEase.state = false
        cmd = 0
    }.overrides(font = Poppins.semiBold, fontSize = 24f, style = Button.Style.PRIMARY, true)

    private var dragX = 0f
    private var dragY = 0f

    private var selectedModule: HUDModule? = null

    fun parent(screen: EScreen) = apply {
        parentScreen = screen
    }

    override fun init() {
        openEase.state = true

        backButton.x = (display.width - backButton.w) / 2f
        backButton.y = (display.height - backButton.h) / 2f
    }

    override fun render(mouseX: Float, mouseY: Float, delta: Float) {
        val (imgX, imgY, imgW, imgH) = Canvas.calcImgSizeAndPos(Images.background_blur)

        Canvas.frame {
            if (client.get().world == null) {
                it.image(imgX, imgY, imgW, imgH, Images.background_blur)
            } else {
            }
            it.pushAlpha(openEase.get()) {
                ModuleManager.modules.values.filter { m -> m is HUDModule && m.enabled }.forEach { m ->
                    m as HUDModule
                    m.hoverEase.state = m.bounds.contains(mouseX, mouseY) || (selectedModule == m)
                    m.dummy()
                    it.round(m.bounds, 8f, paint(Colors.current.text().alpha(m.clickEase.get() * 0.7f)))
                    it.round(m.bounds.expanded(4f + m.clickEase.get()), 12f, paint(Colors.current.text().alpha(m.hoverEase.get())).stroke(2.4f * m.hoverEase.get()))
                }
                it.translate(0f, (1f - openEase.get()) * 100f)
                backButton.render(mouseX, mouseY)
            }
        }

        selectedModule?.let { m ->
            Cursor.set(Cursor.RESIZE_ALL)
            m.bounds.x = (mouseX - dragX).coerceIn(0f, display.width.toFloat() - m.bounds.width)
            m.bounds.y = (mouseY - dragY).coerceIn(0f, display.height.toFloat() - m.bounds.height)

            m.relativeX = m.bounds.x / (display.width - m.bounds.width).toDouble()
            m.relativeY = m.bounds.y / (display.height - m.bounds.height).toDouble()
        }

        if (!openEase.state && openEase.get() <= 0.01) {
            when (cmd) {
                0 -> {
                    client.openScreen(parentScreen)
                    parentScreen = null
                }
            }
        }
    }

    override fun mouse(mouseX: Float, mouseY: Float, button: Int, action: ButtonAction) {
        ModuleManager.modules.values.filter { m -> m is HUDModule && m.enabled }.forEach { m ->
            m as HUDModule
            if (m.bounds.contains(mouseX, mouseY)) {
                when (action) {
                    ButtonAction.PRESS -> {
                        m.clickEase.state = true && selectedModule == null
                        m.clickEase.forceFinish()
                        selectedModule = m
                        dragX = mouseX - m.bounds.x
                        dragY = mouseY - m.bounds.y
                    }
                    ButtonAction.RELEASE -> {
                        if (m.clickEase.state) {
                            selectedModule = null
                            dragX = 0f
                            dragY = 0f
                        }
                        m.clickEase.state = false
                    }
                }
            }
            if (action == ButtonAction.RELEASE) {
                m.clickEase.state = false
                selectedModule = null
                dragX = 0f
                dragY = 0f
            }
        }
        backButton.mouse(mouseX, mouseY, button, action)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        backButton.x = (display.width - backButton.w) / 2f
        backButton.y = (display.height - backButton.h) / 2f

        ModuleManager.modules.values.filter { m -> m is HUDModule && m.enabled }.forEach { m ->
            m as HUDModule
            m.bounds.x = (m.relativeX * (display.width - m.bounds.width)).toFloat()
            m.bounds.y = (m.relativeY * (display.height - m.bounds.height)).toFloat()
        }
    }

    override fun overrideEscape() = openEase.state

    override fun onEscape() {
        openEase.state = false
        cmd = 0
    }
}