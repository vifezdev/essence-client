package cc.essence.core.resource

abstract class Resource(val name: String, protected val path: String) {
    abstract fun load()
    abstract fun unload()
}