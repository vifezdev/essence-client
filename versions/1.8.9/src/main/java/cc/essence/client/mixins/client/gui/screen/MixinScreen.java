package cc.essence.client.mixins.client.gui.screen;

import cc.essence.abstraction.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(net.minecraft.client.gui.screen.Screen.class)
public class MixinScreen implements Screen {}

