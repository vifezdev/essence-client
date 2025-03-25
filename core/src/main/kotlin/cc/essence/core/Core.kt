package cc.essence.core

import cc.essence.bridge.BridgeRegistry
import cc.essence.core.config.Cfg
import cc.essence.core.event.EventBus
import cc.essence.core.event.EventRegister
import cc.essence.core.feature.module.ModuleManager
import cc.essence.core.feature.module.impl.ModuleFPS
import cc.essence.core.feature.module.impl.ModuleKeystrokes
import cc.essence.core.gl.Cursor
import cc.essence.core.render.skia.SkFrame
import cc.essence.core.ui.colors.Colors
import cc.essence.core.version.Version
import com.jthemedetecor.OsThemeDetector
import io.github.oshai.kotlinlogging.KotlinLogging
import org.lwjgl.glfw.GLFW

val logger = KotlinLogging.logger("Essence Core")
//val bus = PubSub.newInstance<Event> { logger.error { "Error occurred in event bus: $it" } }
val bus = EventBus()

object Core {
    var versionString = ""
        private set

    fun init(version: Version) {
        versionString = version.versionString

        ModuleManager.register<ModuleFPS>()
        ModuleManager.register<ModuleKeystrokes>()

        if (OsThemeDetector.isSupported()) {
            OsThemeDetector.getDetector().registerListener {
                Colors.current.isDark = it
            }
        }

        Cfg.init()
        Cfg.loadModules()
        EventRegister()

        GLFW.glfwSetWindowTitle(BridgeRegistry.client().get().handle(), "Essence $versionString")
        logger.info { "Essence Core initialized" } }

    fun shutdown() {
        SkFrame.destroy()
        Cursor.destroy()
        Cfg.saveModules()
        logger.info { "Essence Core shutting down" }
    }
}
