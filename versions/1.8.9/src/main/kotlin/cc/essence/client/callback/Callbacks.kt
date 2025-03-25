package cc.essence.client.callback

import cc.essence.core.bus
import cc.essence.core.event.EventHUD
import cc.essence.core.event.EventTick
import cc.essence.core.logger
import cc.essence.core.render.skia.Canvas
import net.legacyfabric.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.legacyfabric.fabric.api.client.rendering.v1.HudRenderCallback
import net.legacyfabric.fabric.api.entity.event.v1.ServerEntityCombatEvents
import net.legacyfabric.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.legacyfabric.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.entity.player.ServerPlayerEntity

object Callbacks {
    fun register() {
        HudRenderCallback.EVENT.register(HudRenderCallback { _, _ -> Canvas.frame { bus.post(EventHUD()) } })
        ClientTickEvents.START_CLIENT_TICK.register(ClientTickEvents.StartTick { bus.post(EventTick.ClientStart()) })
        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { bus.post(EventTick.ClientEnd()) })
        ClientTickEvents.START_WORLD_TICK.register(ClientTickEvents.StartWorldTick { bus.post(EventTick.WorldStart()) })
        ClientTickEvents.END_WORLD_TICK.register(ClientTickEvents.EndWorldTick { bus.post(EventTick.WorldEnd()) })

    }
}
