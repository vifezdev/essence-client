package cc.essence.core.feature.setting

data class Setting<T>(
    val name: String,
    var value: T,
    val onChange: (T) -> Unit,
    private var visibleIf: (T) -> Boolean = { true },
) {
    operator fun getValue(thisRef: Any?, property: Any?): T {
        return value
    }

    operator fun setValue(thisRef: Any?, property: Any?, value: T) {
        this.value = value
    }

    fun visibleIf(visibleIf: (T) -> Boolean) = apply {
        this.visibleIf = visibleIf
    }

    fun isVisible() = visibleIf(value)
}