package cc.essence.client.mixins.client.options;

import cc.essence.abstraction.client.options.KeyBind;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.client.option.KeyBinding.class)
public abstract class MixinKeyBinding implements KeyBind {
    @Shadow private int code;

    @Shadow private boolean pressed;

    @Shadow @Final private String category;

    @Shadow @Final private String translationKey;

    @Shadow public abstract boolean wasPressed();

    @Override
    public int essence$key() {
        return code;
    }

    @Override
    public boolean essence$pressed() {
        return pressed;
    }

    @Override
    public void essence$pressed(boolean b) {
        pressed = b;
    }

    @Override
    public @NotNull String essence$category() {
        return category;
    }

    @Override
    public @NotNull String essence$translation() {
        return translationKey;
    }

    @Override
    public boolean essence$wasPressed() {
        return wasPressed();
    }
}
