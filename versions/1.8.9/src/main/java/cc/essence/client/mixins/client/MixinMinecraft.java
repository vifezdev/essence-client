package cc.essence.client.mixins.client;

import cc.essence.abstraction.client.Minecraft;
import cc.essence.abstraction.client.options.Options;
import cc.essence.abstraction.client.world.World;
import cc.essence.client.Client;
import cc.essence.client.bridge.ClientImpl;
import cc.essence.core.Core;
import cc.essence.core.CoreKt;
import cc.essence.core.data.Delta;
import cc.essence.core.event.EventResize;
import cc.essence.core.gl.Cursor;
import cc.essence.core.render.skia.SkFrame;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.Callable;

@SuppressWarnings("ALL")
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraft implements Minecraft {
    @Shadow public abstract void stop();

    @Shadow public int width;
    @Shadow public int height;

    @Shadow private Framebuffer fbo;

    @Shadow public Screen currentScreen;

    @Shadow public abstract void setScreen(Screen screen);

    @Shadow public ClientWorld world;

    @Shadow private static MinecraftClient instance;

    @Shadow public GameOptions options;

    @Inject(method = "initializeGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;setClearColor(FFFF)V", shift = At.Shift.AFTER))
    public void Essence$initOpenGL(CallbackInfo ci) {
        Client.INSTANCE.glInit();
    }

    @Inject(method = "runGameLoop", at = @At("HEAD"))
    public void Essence$runGameLoopHEAD(CallbackInfo ci) {
        Delta.INSTANCE.tick(MinecraftClient.getCurrentFps());
    }

    @Inject(method = "resizeFramebuffer", at = @At("TAIL"))
    public void Essence$resizeFramebuffer(CallbackInfo ci) {
        SkFrame.handleResize(this.width, this.height);
        CoreKt.getBus().post(new EventResize(this.width, this.height));
    }

    @ModifyConstant(method = "getMaxFramerate", constant = @Constant(intValue = 30))
    public int getMaxFramerate(int value) {
        return (currentScreen instanceof ClientImpl.ZSC) ? 144 : value;
    }


    @Nullable
    @Override
    public World getWorld() {
        return (World) world;
    }

    @Override
    public void shutdown() {
        Core.INSTANCE.shutdown();
        stop();
    }

    @NotNull
    @Override
    public cc.essence.abstraction.client.gl.Framebuffer mainFbo() {
        return (cc.essence.abstraction.client.gl.Framebuffer) this.fbo;
    }

    @Override
    public void openScreen(@Nullable cc.essence.abstraction.client.gui.screen.Screen screen) {
        setScreen((Screen) screen);
    }

    @Override
    public long handle() {
        return Display.getHandle();
    }

    @Override
    public @NotNull Options getOptions() {
        return (Options) options;
    }
}
