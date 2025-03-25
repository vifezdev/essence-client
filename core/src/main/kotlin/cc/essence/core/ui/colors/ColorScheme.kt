package cc.essence.core.ui.colors

import cc.essence.core.animation.Ease
import cc.essence.core.data.interpolate

class ColorScheme(
    val primary: Int,
    val secondary: Int,
    val background: Int,
    val surface: Int,
    val text: Int,
    val textSecondary: Int,
    val enabled: Int,
    val disabled: Int,
    val error: Int,
    val warn: Int,
    val info: Int,
    val success: Int
)

object Colors {
    val light = ColorScheme(
        primary = 0xFF374ED5.toInt(),
        secondary = 0xFF38d6ca.toInt(),
        background = 0xFFf7f7f7.toInt(),
        surface = 0xFFfafafa.toInt(),
        text = 0xFF000000.toInt(),
        textSecondary = 0x8A000000.toInt(),
        enabled = 0xFF6200EE.toInt(),
        disabled = 0x61000000.toInt(),
        error = 0xFFB00020.toInt(),
        warn = 0xFFFFAB00.toInt(),
        info = 0xFF018786.toInt(),
        success = 0xFF00C853.toInt()
    )

    val dark = ColorScheme(
        primary = 0xFF374ED5.toInt(),
        secondary = 0xFF38d6ca.toInt(),
        background = 0xFF262626.toInt(),
        surface = 0xFF1c1c1c.toInt(),
        text = 0xFFFFFFFF.toInt(),
        textSecondary = 0xFF8A8A8A.toInt(),
        enabled = 0xFFBB86FC.toInt(),
        disabled = 0x61FFFFFF.toInt(),
        error = 0xFFCF6679.toInt(),
        warn = 0xFFFFAB00.toInt(),
        info = 0xFF03DAC6.toInt(),
        success = 0xFF4CAF50.toInt()
    )

    val current = AnimatedColorScheme(light, dark)
}

class AnimatedColorScheme(val light: ColorScheme, val dark: ColorScheme) {
    val ease = Ease { 300f }.defaultState { true }

    var isDark: Boolean = true
        set(value) {
            field = value
            ease.state = value
        }

    fun primary() = light.primary.interpolate(dark.primary, ease.get())
    fun secondary() = light.secondary.interpolate(dark.secondary, ease.get())
    fun background() = light.background.interpolate(dark.background, ease.get())
    fun surface() = light.surface.interpolate(dark.surface, ease.get())
    fun text() = light.text.interpolate(dark.text, ease.get())
    fun textSecondary() = light.textSecondary.interpolate(dark.textSecondary, ease.get())
    fun enabled() = light.enabled.interpolate(dark.enabled, ease.get())
    fun disabled() = light.disabled.interpolate(dark.disabled, ease.get())
    fun error() = light.error.interpolate(dark.error, ease.get())
    fun warn() = light.warn.interpolate(dark.warn, ease.get())
    fun info() = light.info.interpolate(dark.info, ease.get())
    fun success() = light.success.interpolate(dark.success, ease.get())
}