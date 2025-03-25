package cc.essence.core.ui.gui

import cc.essence.core.data.interpolate
import cc.essence.core.render.skia.Alignment
import cc.essence.core.render.skia.Canvas
import cc.essence.core.render.skia.Canvas.paint
import cc.essence.core.render.skia.Canvas.stroke
import cc.essence.core.resource.font.objects.WixMFD
import cc.essence.core.ui.ScreenMapper
import cc.essence.core.ui.ScreenType
import cc.essence.core.ui.colors.Colors
import cc.essence.core.ui.elements.Button
import io.github.humbleui.skija.FontSlant
import io.github.humbleui.skija.FontWeight
import io.github.humbleui.skija.FontWidth
import org.lwjgl.glfw.GLFW

class GameMenu : EScreen() {
    private val buttons = mutableListOf<Button>()

    override fun init() {
        val btnW = 320f
        buttons.clear()
        buttons.add(Button("Back To Game", display.width / 2f - btnW / 2f, display.height / 2f - 145f, btnW, 50f) {
            client.get().openScreen(null)
        }.overrides(style = Button.Style.PRIMARY, hoverOverlay = true))
        buttons.add(Button(
            "Achievements", display.width / 2f - btnW / 2f, display.height / 2f + 15 - 100f, btnW / 2f - 5f, 50f
        ) {
            ScreenMapper.open(ScreenType.ACHIEVEMENTS, this)
        })
        buttons.add(Button("Statistics", display.width / 2f + 5, display.height / 2f + 15 - 100f, btnW / 2f - 5f, 50f) {
            ScreenMapper.open(ScreenType.STATISTICS, this)
        })
        buttons.add(Button("ClickGUI", display.width / 2f - btnW / 2f, display.height / 2f - 25, btnW, 50f) {
            client.openScreen(Screens.CLICK_GUI)
        })
        buttons.add(Button("Options", display.width / 2f - btnW / 2f, display.height / 2f + 35, btnW / 2f - 5f, 50f) {
            ScreenMapper.open(ScreenType.SETTINGS, this)
        })
        buttons.add(Button("Open To Lan", display.width / 2f + 5, display.height / 2f + 35, btnW / 2f - 5f, 50f) {
            ScreenMapper.open(ScreenType.OPEN_TO_LAN, this)
        })
        buttons.add(Button("Exit", display.width / 2f - btnW / 2f, display.height / 2f + 95, btnW, 50f) {
            ScreenMapper.open(ScreenType.EXIT_WORLD)
        })
    }

    override fun render(mouseX: Float, mouseY: Float, delta: Float) {
        val fw = 360f
        val fh = 340f

        Canvas.frame {
            it.round(
                (display.width - fw) / 2f,
                (display.height - fh) / 2f,
                fw,
                fh,
                10f,
                paint(Colors.current.surface())
            )
            it.round(
                (display.width - fw) / 2f + 4f,
                (display.height - fh) / 2f + 4f,
                fw - 8f,
                fh - 8f,
                6f,
                paint(Colors.current.background()).stroke(2f)
            )
            Canvas.SkSl.lineEffect(
                (display.width - fw) / 2f + 4f,
                (display.height - fh) / 2f + 4f,
                fw - 8f,
                fh - 8f,
                6f,
                Colors.current.surface(),
                Colors.current.surface().interpolate(Colors.current.background(), 0.6f),
                10f,
                GLFW.glfwGetTime().toFloat() / 4
            )
            buttons.forEach { it.render(mouseX, mouseY) }
        }
    }

    override fun mouse(mouseX: Float, mouseY: Float, button: Int, action: ButtonAction) {
        buttons.forEach { it.mouse(mouseX, mouseY, button, action) }
    }

    override fun pauseGame() = true
}