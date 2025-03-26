package cc.essence.bridge.impl

import cc.essence.abstraction.client.Minecraft
import cc.essence.bridge.Bridge
import cc.essence.core.ui.gui.EScreen
import cc.essence.abstraction.client.entity.LivingEntity // Assuming player is a LivingEntity

abstract class IClient : Bridge {
    abstract fun openScreen(eScreen: EScreen?)

    abstract fun get(): Minecraft
    abstract val fps: Int
    
    val player: LivingEntity
        get() = get().getPlayer() as LivingEntity
}