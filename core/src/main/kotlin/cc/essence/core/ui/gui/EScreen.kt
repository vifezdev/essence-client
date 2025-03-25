package cc.essence.core.ui.gui

import cc.essence.bridge.BridgeRegistry
import cc.essence.core.data.Delta

abstract class EScreen {
    protected val display = BridgeRegistry.display()
    protected val client = BridgeRegistry.client()


    abstract fun init()
    abstract fun render(mouseX: Float, mouseY: Float, delta: Float = Delta.time)
    abstract fun mouse(mouseX: Float, mouseY: Float, button: Int, action: ButtonAction)
    open fun close() {}
    open fun pauseGame() = true
    open fun overrideEscape() = false
    open fun onEscape() {}

    open fun resize(width: Int, height: Int) {}
}

enum class ButtonAction {
    PRESS, RELEASE
}