package cc.essence.core.ui

object ScreenMapper {
    private val map = mutableMapOf<ScreenType,(Array<Any>?)->Unit>()

    fun map(vararg pair: Pair<ScreenType,(Array<Any>?)->Unit>) {
        pair.forEach {
            map[it.first] = it.second
        }
    }

    fun open(screenType: ScreenType, vararg args: Any) {
        map[screenType]?.invoke(args.toList().toTypedArray())
    }
}

enum class ScreenType {
    SETTINGS, MULTIPLAYER, SELECT_WORLD, MOD_MENU, ACHIEVEMENTS, STATISTICS, OPEN_TO_LAN, EXIT_WORLD
}