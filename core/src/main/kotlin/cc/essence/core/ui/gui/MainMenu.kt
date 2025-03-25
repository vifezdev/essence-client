@file:Suppress("PrivatePropertyName", "unused", "SpellCheckingInspection")

package cc.essence.core.ui.gui

import cc.essence.core.Core
import cc.essence.core.animation.Ease
import cc.essence.core.animation.Easing
import cc.essence.core.render.skia.Alignment
import cc.essence.core.render.skia.Canvas
import cc.essence.core.render.skia.Canvas.paint
import cc.essence.core.resource.font.objects.Icons
import cc.essence.core.resource.font.objects.LA
import cc.essence.core.resource.font.objects.MR
import cc.essence.core.resource.font.objects.Poppins
import cc.essence.core.resource.image.Images
import cc.essence.core.ui.ScreenType
import cc.essence.core.ui.ScreenMapper
import cc.essence.core.ui.elements.Button
import cc.essence.core.ui.elements.Element

class MainMenu : EScreen() {
    private val menuButtons = mutableListOf<Element>()
    private val playButtons = mutableListOf<Element>()

    private val playEase = Ease(Easing.EXPO_IN_OUT) { 400f }
    private val openEase = Ease(Easing.EXPO_IN_OUT) { 400f }

    private var cmd = 0

    private val CMD_CLICKGUI = 1
    private val CMD_OPTIONS = 2
    private val CMD_SINGEPLAYER = 3
    private val CMD_MULTIPLAYER = 4
    private val CMD_FABRIC_MODS = 5

    override fun init() {
        val buttonWidth = 240f

        menuButtons.clear()
        playButtons.clear()

        openEase.state = true

        val yPush = 30f

        cmd = 0

        menuButtons.add(
            Button(
                "Play", display.width / 2f - buttonWidth / 2f, display.height / 2f - 50f - 5f + yPush, buttonWidth, 50f
            ) {
                playEase.state = true
            }.overrides(font = Poppins.semiBold, fontSize = 24f, style = Button.Style.PRIMARY, true)
        )
        menuButtons.add(Button("ClickGUI", display.width / 2f - 60f, display.height / 2f + 5f + yPush, 120f, 50f) {
            openEase.state = false
            cmd = CMD_CLICKGUI
        })
        menuButtons.add(
            Button(
                LA { cog }, display.width / 2f - buttonWidth / 2f, display.height / 2f + 5f + yPush, 50f, 50f
            ) {
                openEase.state = false
                cmd = CMD_OPTIONS
            }.overrides(font = Icons.lineAwesome, fontSize = 24f)
        )
        menuButtons.add(
            Button(
                MR { close }, display.width / 2f + buttonWidth / 2f - 50f, display.height / 2f + 5f + yPush, 50f, 50f
            ) {
                client.get().shutdown()
            }.overrides(font = Icons.materialRound, fontSize = 24f)
        )
        menuButtons.add(
            Button(
                LA { puzzle_piece }, display.width - 60f, 10f, 50f, 50f
            ) {
                openEase.state = false
                cmd = CMD_FABRIC_MODS
            }.overrides(font = Icons.lineAwesome, fontSize = 24f)
        )

        playButtons.add(
            Button(
                "Singleplayer", display.width / 2f - buttonWidth / 2f, display.height / 2f - 50f - 5f, buttonWidth, 50f
            ) {
                playEase.state = false
                openEase.state = false
                cmd = CMD_SINGEPLAYER
            }.overrides(font = Poppins.semiBold, fontSize = 24f, style = Button.Style.PRIMARY, true)
        )
        playButtons.add(
            Button(
                "Multiplayer", display.width / 2f - buttonWidth / 2f, display.height / 2f + 5f, buttonWidth, 50f
            ) {
                playEase.state = false
                openEase.state = false
                cmd = CMD_MULTIPLAYER
            }.overrides(font = Poppins.semiBold, fontSize = 24f, style = Button.Style.PRIMARY, true)
        )
    }

    override fun render(mouseX: Float, mouseY: Float, delta: Float) {
        val (imgX, imgY, imgW, imgH) = Canvas.calcImgSizeAndPos(Images.background_blur)

        Canvas.frame {
            it.image(imgX, imgY, imgW, imgH, Images.background_blur)
            it.pushAlpha(((1f - playEase.get()) * openEase.get())) {
                it.translate(0f, (1f - openEase.get()) * 100f)
                it.translate(display.width / 2f, display.height / 2f)
                it.scale(1f + playEase.get() * 0.4f, 1f + playEase.get() * 0.4f)
                it.translate(-display.width / 2f, -display.height / 2f)
                menuButtons.forEach { element ->
                    element.render(if (playEase.get() > 0.01) 0f else mouseX, if (playEase.get() > 0.01) 0f else mouseY)
                }
            }
            it.pushAlpha(playEase.get()) {
                it.translate(display.width / 2f, display.height / 2f)
                it.scale(0.8f + playEase.get() * 0.2f, 0.8f + playEase.get() * 0.2f)
                it.translate(-display.width / 2f, -display.height / 2f)
                playButtons.forEach { element ->
                    element.render(if (playEase.get() < 0.99) 0f else mouseX, if (playEase.get() < 0.99) 0f else mouseY)
                }
            }

            it.pushAlpha(openEase.get()) {
                it.text(
                    "Essence Client ${Core.versionString}",
                    8f,
                    display.height - 8f,
                    14f,
                    paint(0x80FFFFFF),
                    align = Alignment.LEFT_BOTTOM
                )
                it.text(
                    "Copyright Mojang AB. Do no distribute!",
                    display.width - 8f,
                    display.height - 8f,
                    14f,
                    paint(0x80FFFFFF),
                    align = Alignment.RIGHT_BOTTOM
                )

                it.text(
                    "Essence Client",
                    display.width / 2f,
                    display.height / 2f - 60f - 60f * playEase.get(),
                    34f,
                    font = Poppins.bold,
                    align = Alignment.CENTER_BOTTOM
                )

                it.image(
                    display.width / 2f - 64, display.height / 2f - 64 - 160f - 60f * playEase.get(), 128f, 128f, Images.logo
                )
            }
        }

        if (!openEase.state && openEase.get() <= 0.01) {
            when (cmd) {
                CMD_CLICKGUI -> {
                    client.openScreen(Screens.CLICK_GUI.parent(this))
                }

                CMD_OPTIONS -> {
                    ScreenMapper.open(ScreenType.SETTINGS, this)
                }

                CMD_SINGEPLAYER -> {
                    ScreenMapper.open(ScreenType.SELECT_WORLD, this)
                }

                CMD_MULTIPLAYER -> {
                    ScreenMapper.open(ScreenType.MULTIPLAYER, this)
                }
                CMD_FABRIC_MODS -> {
                    ScreenMapper.open(ScreenType.MOD_MENU, this)
                }
            }
        }
    }

    override fun mouse(mouseX: Float, mouseY: Float, button: Int, action: ButtonAction) {
        if (!playEase.state && openEase.state) {
            menuButtons.forEach { element ->
                element.mouse(mouseX, mouseY, button, action)
            }
        } else {
            playButtons.forEach { element ->
                element.mouse(mouseX, mouseY, button, action)
            }
        }
    }

    override fun overrideEscape() = playEase.state

    override fun onEscape() {
        playEase.state = false
    }
}