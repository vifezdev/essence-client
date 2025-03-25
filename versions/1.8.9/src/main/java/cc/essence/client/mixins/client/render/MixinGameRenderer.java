package cc.essence.client.mixins.client.render;

import cc.essence.core.CoreKt;
import cc.essence.core.event.EventRender3D;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(method = "renderWorld(IFJ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 18, shift = At.Shift.BEFORE))
    public void Essence$renerWorld(CallbackInfo ci) {
        CoreKt.getBus().post(new EventRender3D());
    }
}
