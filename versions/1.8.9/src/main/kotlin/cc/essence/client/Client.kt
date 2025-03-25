package cc.essence.client

import cc.essence.client.bridge.ClientImpl
import cc.essence.client.bridge.DisplayImpl
import cc.essence.client.bridge.FramebufferImpl
import cc.essence.client.bridge.InputImpl
import cc.essence.client.callback.Callbacks
import cc.essence.core.Core
import cc.essence.core.feature.module.ModuleManager
import cc.essence.core.gl.GLZ
import cc.essence.core.ui.ScreenType
import cc.essence.core.ui.ScreenMapper
import cc.essence.core.ui.gui.EScreen
import cc.essence.core.ui.gui.Screens
import cc.essence.core.version.Version
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.legacyfabric.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.AchievementsScreen
import net.minecraft.client.gui.screen.OpenToLanScreen
import net.minecraft.client.gui.screen.SettingsScreen
import net.minecraft.client.gui.screen.StatsScreen
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen
import net.minecraft.client.gui.screen.world.SelectWorldScreen
import net.minecraft.client.option.GameOptions
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.gui.screen.Screen as McScreen
import org.lwjgl.input.Keyboard
import cc.essence.bridge.BridgeRegistry as bridge

@Environment(EnvType.CLIENT)
object Client : ClientModInitializer, Version("1.8.9") {
    override fun glInit() {
        super.glInit()
        Core.init(this)
    }

    override fun onInitializeClient() {
        bridge.register<ClientImpl>()
        bridge.register<DisplayImpl>()
        bridge.register<FramebufferImpl>()
        bridge.register<InputImpl>()

        //TODO: put this in a separate class so it doesnt look so ugly 😫
        ScreenMapper.map(ScreenType.SETTINGS to { args ->
            val screen = (if (args?.get(0) is EScreen) ClientImpl.ZSC(args[0] as EScreen) else args?.get(0) as McScreen)
            MinecraftClient.getInstance().setScreen(SettingsScreen(screen, MinecraftClient.getInstance().options))
        }, ScreenType.MULTIPLAYER to { args ->
            val screen = (if (args?.get(0) is EScreen) ClientImpl.ZSC(args[0] as EScreen) else args?.get(0) as McScreen)
            MinecraftClient.getInstance().setScreen(MultiplayerScreen(screen))
        }, ScreenType.SELECT_WORLD to { args ->
            val screen = (if (args?.get(0) is EScreen) ClientImpl.ZSC(args[0] as EScreen) else args?.get(0) as McScreen)
            MinecraftClient.getInstance().setScreen(SelectWorldScreen(screen))
        }, ScreenType.MOD_MENU to { args ->
            val screen = (if (args?.get(0) is EScreen) ClientImpl.ZSC(args[0] as EScreen) else args?.get(0) as McScreen)
            val clazz = Class.forName("io.github.prospector.modmenu.gui.ModsScreen")
            MinecraftClient.getInstance().setScreen(clazz.constructors[0].newInstance(screen) as McScreen)
        }, ScreenType.ACHIEVEMENTS to { args ->
            val screen = (if (args?.get(0) is EScreen) ClientImpl.ZSC(args[0] as EScreen) else args?.get(0) as McScreen)
            MinecraftClient.getInstance()
                .setScreen(AchievementsScreen(screen, MinecraftClient.getInstance().player.statHandler))
        }, ScreenType.STATISTICS to { args ->
            val screen = (if (args?.get(0) is EScreen) ClientImpl.ZSC(args[0] as EScreen) else args?.get(0) as McScreen)
            MinecraftClient.getInstance()
                .setScreen(StatsScreen(screen, MinecraftClient.getInstance().player.statHandler))
        }, ScreenType.OPEN_TO_LAN to { args ->
            val screen = (if (args?.get(0) is EScreen) ClientImpl.ZSC(args[0] as EScreen) else args?.get(0) as McScreen)
            MinecraftClient.getInstance().setScreen(OpenToLanScreen(screen))
        }, ScreenType.EXIT_WORLD to {
            MinecraftClient.getInstance().world.disconnect()
            MinecraftClient.getInstance().connect(null)
            if (MinecraftClient.getInstance().isIntegratedServerRunning) {
                MinecraftClient.getInstance().setScreen(ClientImpl.ZSC(Screens.MAIN_MENU))
            } else {
                MinecraftClient.getInstance().setScreen(MultiplayerScreen(ClientImpl.ZSC(Screens.MAIN_MENU)))
            }
        })

        GLZ.setLegacy()
        Callbacks.register()

        KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "key.essence.clickgui", Keyboard.KEY_LSHIFT, "key.categories.essence"
            )
        )
    }
}