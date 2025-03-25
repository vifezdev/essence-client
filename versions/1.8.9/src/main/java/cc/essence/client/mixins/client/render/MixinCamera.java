package cc.essence.client.mixins.client.render;

import cc.essence.client.access.View;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

@Mixin(Camera.class)
public class MixinCamera {
    @Shadow @Final private static FloatBuffer MODEL_MATRIX;
    @Shadow @Final private static FloatBuffer PROJECTION_MATRIX;
    @Shadow @Final private static IntBuffer VIEWPORT;

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lorg/lwjgl/util/glu/GLU;gluUnProject(FFFLjava/nio/FloatBuffer;Ljava/nio/FloatBuffer;Ljava/nio/IntBuffer;Ljava/nio/FloatBuffer;)Z", shift = At.Shift.BEFORE))
    private static void Essence$update(CallbackInfo ci) {
        View.INSTANCE.setPROJECTION_MATRIX(PROJECTION_MATRIX);
        View.INSTANCE.setVIEWPORT(VIEWPORT);
        View.INSTANCE.setMODEL_MATRIX(MODEL_MATRIX);
    }
}
