package cc.essence.bridge.impl

import cc.essence.abstraction.client.Minecraft
import cc.essence.bridge.Bridge
import cc.essence.core.ui.gui.EScreen

abstract class IClient : Bridge {
    abstract fun openScreen(eScreen: EScreen?)

    abstract fun get(): Minecraft
    abstract val fps: Int
}