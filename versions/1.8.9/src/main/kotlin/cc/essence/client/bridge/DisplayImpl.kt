package cc.essence.client.bridge

import cc.essence.bridge.impl.IDisplay
import org.lwjgl.opengl.Display

class DisplayImpl : IDisplay() {
    override var width: Int
        get() = Display.width
        set(value) { Display.width = value }
    override var height: Int
        get() = Display.height
        set(value) { Display.height = value }
    override var title: String
        get() = Display.title
        set(value) { Display.title = value }
}