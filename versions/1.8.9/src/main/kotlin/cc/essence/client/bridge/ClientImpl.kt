package cc.essence.client.bridge

import cc.essence.abstraction.client.Minecraft
import cc.essence.bridge.impl.IClient
import cc.essence.core.gl.Cursor
import cc.essence.core.gl.Cursor.set
import cc.essence.core.ui.gui.ButtonAction
import cc.essence.core.ui.gui.EScreen
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.Display

class ClientImpl : IClient() {
    override fun openScreen(eScreen: EScreen?) {
        val zsc = if (eScreen == null) null else ZSC(eScreen)
        MinecraftClient.getInstance().setScreen(zsc)
    }

    override fun get() = MinecraftClient.getInstance() as Minecraft
    override val fps: Int
        get() = MinecraftClient.getCurrentFps()

    class ZSC(private val screen: EScreen): Screen() {
        override fun init() {
            super.init()
            screen.init()
        }

        override fun render(mouseX: Int, mouseY: Int, tickDelta: Float) {
            set(Cursor.ARROW)

            super.render(mouseX, mouseY, tickDelta)
            screen.render(Mouse.getX().toFloat(), Display.height.toFloat() - Mouse.getY().toFloat())
        }

        override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
            super.mouseClicked(mouseX, mouseY, button)
            screen.mouse(Mouse.getX().toFloat(), Display.height.toFloat() - Mouse.getY().toFloat(), button, ButtonAction.PRESS)
        }

        override fun mouseReleased(mouseX: Int, mouseY: Int, button: Int) {
            super.mouseReleased(mouseX, mouseY, button)
            screen.mouse(Mouse.getX().toFloat(), Display.height.toFloat() - Mouse.getY().toFloat(), button, ButtonAction.RELEASE)
        }

        override fun removed() {
            super.removed()
            screen.close()
        }

        override fun shouldPauseGame(): Boolean {
            return screen.pauseGame()
        }

        override fun handleKeyboard() {
            if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && screen.overrideEscape()) {
                screen.onEscape()
            } else {
                super.handleKeyboard()
            }
        }

        override fun resize(client: MinecraftClient?, width: Int, height: Int) {
            super.resize(client, width, height)
            screen.resize(width, height)
        }
    }
}