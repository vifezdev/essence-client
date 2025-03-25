package cc.essence.core.feature.setting

class SettingsGroup(val name: String) {
    private val settings = mutableListOf<Setting<*>>()

    fun <T> add(setting: Setting<T>) {
        settings.add(setting)
    }

    operator fun <T> get(name: String): Setting<T>? {
        @Suppress("UNCHECKED_CAST")
        return settings.find { it.name == name } as? Setting<T>
    }

    override fun toString(): String {
        return "SettingsGroup(name='$name', settings=$settings)"
    }
}