package cc.essence.core.event

import cc.essence.abstraction.client.entity.Entity
import cc.essence.abstraction.client.entity.LivingEntity
import cc.essence.core.bus
import cc.essence.core.feature.module.HUDModule
import cc.essence.core.feature.module.ModuleManager

open class Event {
    var cancelled = false
        private set

    fun cancel() {
        cancelled = true
    }
}

// MISC EVENTS
class EventResize(val width: Int, val height: Int) : Event()

// RENDER EVENTS
class EventHUD: Event()
class EventRender3D : Event()

// TICK EVENTS
open class EventTick: Event() {
    class ClientStart : EventTick()
    class ClientEnd : EventTick()
    class WorldStart : EventTick()
    class WorldEnd : EventTick()
}

// ENTITY EVENTS
class EventEntityHurts(val entity: LivingEntity, val attacker: LivingEntity?, val amount: Float) : Event() {
    operator fun component1() = entity
    operator fun component2() = attacker
    operator fun component3() = amount
}

class EventRegister {
    init {
        bus.on<EventResize> {
            ModuleManager.modules.values.filterIsInstance<HUDModule>().forEach { (it).updatePosition() }
        }
    }
}