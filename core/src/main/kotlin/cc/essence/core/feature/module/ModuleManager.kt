package cc.essence.core.feature.module

import kotlin.reflect.KClass

object ModuleManager {
    val modules = mutableMapOf<KClass<out Module>, Module>()

    inline fun <reified T : Module> get(): T {
        return modules[T::class] as T
    }

    inline fun <reified T : Module> register(module: T) {
        modules[T::class] = module
    }

    inline fun <reified T : Module> register() {
        modules[T::class] = T::class.java.constructors.first().newInstance() as T
    }

    operator fun get(id: Short): Module? {
        return modules.values.firstOrNull { it.id == id }
    }
}