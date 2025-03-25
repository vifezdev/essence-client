package cc.essence.core.event

@Suppress("UNCHECKED_CAST")
class EventBus {
    private val listeners = mutableMapOf<Class<*>, MutableList<(Event) -> Unit>>()
    private val onceListeners = mutableMapOf<Class<*>, MutableList<(Event) -> Unit>>()

    fun post(event: Event) {
        listeners[event::class.java]?.forEach { it(event) }
        onceListeners[event::class.java]?.forEach { it(event) }
        onceListeners[event::class.java]?.clear()
    }

    fun <T : Event> on(eventType: Class<T>, listener: (T) -> Unit) {
        listeners.computeIfAbsent(eventType) { mutableListOf() }.add(listener as (Event) -> Unit)
    }

    fun <T : Event> once(eventType: Class<T>, listener: (T) -> Unit) {
        onceListeners.computeIfAbsent(eventType) { mutableListOf() }.add(listener as (Event) -> Unit)
    }

    inline fun <reified T : Event> on(noinline listener: (T) -> Unit) = on(T::class.java, listener)
    inline fun <reified T : Event> once(noinline listener: (T) -> Unit) = once(T::class.java, listener)
}