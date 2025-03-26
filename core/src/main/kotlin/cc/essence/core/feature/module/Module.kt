package cc.essence.core.feature.module

import cc.essence.bridge.BridgeRegistry
import cc.essence.core.data.SliderData
import cc.essence.core.feature.setting.Setting
import cc.essence.core.feature.setting.SettingsGroup

@Suppress("ktIdIsJavaKw")
abstract class Module(val name: String, val id: Short) {
    companion object {
        var idCounter: Short = 0
    }

    val settings = mutableListOf<SettingsGroup>()

    protected val client = BridgeRegistry.client()
    protected val minecraft = client.get()
    protected val display = BridgeRegistry.display()

    var enabled = false
        set(value) {
            if (field != value) {
                field = value
                if (value) {
                    onEnable()
                } else {
                    onDisable()
                }
            }
        }

    open fun onEnable() {}
    open fun onDisable() {}

    fun group(name: String): SettingsGroup {
        val group = SettingsGroup(name)
        this.settings.add(group)
        return group
    }

    private fun <T> setting(group: SettingsGroup, name: String, value: T, onChange: (T) -> Unit = {}): Setting<T> {
        return Setting(name, value, onChange).also { group.add(it) }
    }

    fun boolean(group: SettingsGroup, name: String, value: Boolean, onChange: (Boolean) -> Unit = {}) = setting(group, name, value, onChange)
    private fun <T : Number> number(group: SettingsGroup, name: String, data: SliderData<T>, onChange: (SliderData<T>) -> Unit = {}) =
        setting(group, name, data, onChange)

    fun float(
        group: SettingsGroup,
        name: String,
        current: Float,
        range: ClosedFloatingPointRange<Float>,
        step: Float = 1f,
        onChange: (SliderData<Float>) -> Unit = {},
    ): Setting<SliderData<Float>> = number(group, name, SliderData(current, range.start, range.endInclusive, step), onChange)

    fun double(
        group: SettingsGroup,
        name: String,
        current: Double,
        range: ClosedFloatingPointRange<Double>,
        step: Double = 1.0,
        onChange: (SliderData<Double>) -> Unit = {},
    ) = number(group, name, SliderData(current, range.start, range.endInclusive, step), onChange)

    fun int(group: SettingsGroup, name: String, current: Int, range: IntRange, step: Int = 1, onChange: (SliderData<Int>) -> Unit = {}) =
        number(group, name, SliderData(current, range.first, range.last, step), onChange)
}