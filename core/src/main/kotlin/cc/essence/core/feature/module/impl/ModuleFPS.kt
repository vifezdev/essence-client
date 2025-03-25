package cc.essence.core.feature.module.impl

class ModuleFPS : TextModule("FPS") {
    override fun getText(dummy: Boolean): String {
        return "FPS: ${if (dummy) "144" else client.fps}"
    }
}