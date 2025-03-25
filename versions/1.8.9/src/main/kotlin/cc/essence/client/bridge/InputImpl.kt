package cc.essence.client.bridge

import cc.essence.bridge.impl.IInput
import org.lwjgl.input.Keyboard

class InputImpl : IInput() {
    override fun getKeyName(key: Int): String = Keyboard.getKeyName(key)
}