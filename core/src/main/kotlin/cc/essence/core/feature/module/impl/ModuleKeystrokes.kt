package cc.essence.core.feature.module.impl

import cc.essence.abstraction.client.options.KeyBind
import cc.essence.bridge.BridgeRegistry
import cc.essence.core.animation.Ease
import cc.essence.core.data.alpha
import cc.essence.core.data.interpolate
import cc.essence.core.feature.module.HUDModule
import cc.essence.core.render.skia.Alignment
import cc.essence.core.render.skia.Canvas
import cc.essence.core.render.skia.Canvas.paint
import cc.essence.core.resource.font.objects.Poppins
import cc.essence.core.ui.colors.Colors
import org.lwjgl.glfw.GLFW

private const val KEY_SIZE = 50f
private const val GAP = 5f
private const val SIZE = KEY_SIZE * 3 + GAP * 2

class ModuleKeystrokes : HUDModule("Keystrokes") {
    private val keys = arrayOf(
        Key(BridgeRegistry.client().get().options.forwardKey),
        Key(BridgeRegistry.client().get().options.leftKey),
        Key(BridgeRegistry.client().get().options.backKey),
        Key(BridgeRegistry.client().get().options.rightKey),
        Key(BridgeRegistry.client().get().options.jumpKey),
    )

    init {
        bounds.width = SIZE
        bounds.height = SIZE
    }

    override fun render() {
        val x = bounds.x
        val y = bounds.y

        keys[0].shadow(x + KEY_SIZE + GAP, y, KEY_SIZE, KEY_SIZE)
        keys[1].shadow(x, y + KEY_SIZE + GAP, KEY_SIZE, KEY_SIZE)
        keys[2].shadow(x + KEY_SIZE + GAP, y + KEY_SIZE + GAP, KEY_SIZE, KEY_SIZE)
        keys[3].shadow(x + KEY_SIZE * 2 + GAP * 2, y + KEY_SIZE + GAP, KEY_SIZE, KEY_SIZE)
        keys[4].shadow(x, y + KEY_SIZE * 2 + GAP * 2, KEY_SIZE, SIZE)

        keys[0].render(x + KEY_SIZE + GAP, y, KEY_SIZE, KEY_SIZE)
        keys[1].render(x, y + KEY_SIZE + GAP, KEY_SIZE, KEY_SIZE)
        keys[2].render(x + KEY_SIZE + GAP, y + KEY_SIZE + GAP, KEY_SIZE, KEY_SIZE)
        keys[3].render(x + KEY_SIZE * 2 + GAP * 2, y + KEY_SIZE + GAP, KEY_SIZE, KEY_SIZE)
        keys[4].render(x, y + KEY_SIZE * 2 + GAP * 2, KEY_SIZE, SIZE)
    }

    override fun dummy() { render() }
}

internal class Key(private val bind: KeyBind) {
    private val anim = Ease { 180f }

    fun render(x: Float, y: Float, h: Float, w: Float) {
        anim.state = bind.pressed
        val bgClr = Colors.current.background().interpolate(Colors.current.text(), anim.get())
        val textClr = Colors.current.text().interpolate(Colors.current.background(), anim.get())
        val text = BridgeRegistry.input().getKeyName(bind.key)
        Canvas.round(x, y, w, h, 8f, paint(bgClr))
        if (bind.key == BridgeRegistry.client().get().options.jumpKey.key) {
            val s = 5f
            Canvas.round(x + h / 2f - s / 2f, y + h / 2f - s / 2f, w - h + s, s, 8f, paint(textClr))
            return
        }
        Canvas.text(text, x + w / 2 - .5f, y + h / 2 - .5f, 18f, paint(textClr), Alignment.CENTER_MIDDLE, Poppins.semiBold)
    }

    fun shadow(x: Float, y: Float, h: Float, w: Float) {
        Canvas.shadow(x, y, w, h, 8f, 4f, 10f, Colors.current.surface().alpha(.4f))
    }
}