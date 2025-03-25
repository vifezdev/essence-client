package cc.essence.core.util

fun interface Invoker<T> {
    fun invoke(canvas: T)
}