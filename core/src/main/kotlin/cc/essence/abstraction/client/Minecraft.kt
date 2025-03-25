package cc.essence.abstraction.client

import cc.essence.abstraction.client.gl.Framebuffer
import cc.essence.abstraction.client.gui.screen.Screen
import cc.essence.abstraction.client.options.Options
import cc.essence.abstraction.client.world.World

interface Minecraft {
    val world: World?
    val options: Options
    fun shutdown()
    fun mainFbo(): Framebuffer
    fun openScreen(screen: Screen?)
    fun handle(): Long
}