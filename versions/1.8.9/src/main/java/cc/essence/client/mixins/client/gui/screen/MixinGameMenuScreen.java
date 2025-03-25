package cc.essence.client.mixins.client.gui.screen;

import cc.essence.bridge.BridgeRegistry;
import cc.essence.core.ui.gui.Screens;
import net.minecraft.client.gui.screen.GameMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class MixinGameMenuScreen {
    @Inject(method = "init", at = @At("HEAD"))
    public void Essence$init(CallbackInfo ci) {
        BridgeRegistry.client().openScreen(Screens.GAME_MENU);
    }
}
