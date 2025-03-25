package cc.essence.core.resource

import cc.essence.core.resource.font.Font
import cc.essence.core.resource.image.Image

object ResourceManager {
    val resources = mutableListOf<Resource>()

    fun loadFonts(vararg pair: Pair<String, String>) {
        pair.forEach { (name, path) ->
            resources.add(Font(name, "/fonts/$path").apply { load() })
        }
    }

    fun loadImages(vararg pair: Pair<String, String>) {
        pair.forEach { (name, path) ->
            resources.add(Image(name, "/images/$path").apply { load() })
        }
    }

    @JvmStatic
    inline fun <reified T: Resource> find(name: String) = resources.find { it.name == name } as T

    @JvmStatic
    fun getFont(name: String) = find<Font>(name)

    @JvmStatic
    fun getImage(name: String) = find<Image>(name)

    @JvmStatic
    fun unloadAll() {
        resources.forEach { it.unload() }
    }
}