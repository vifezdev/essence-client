package cc.essence.client.mixins.client.options;

import cc.essence.abstraction.client.options.KeyBind;
import cc.essence.abstraction.client.options.Options;
import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(net.minecraft.client.option.GameOptions.class)
public class MixinGameOptions implements Options {
    @Shadow public float sensitivity;

    @Shadow public int viewDistance;

    @Shadow public boolean bobView;

    @Shadow public boolean fbo;

    @Shadow public int maxFramerate;

    @Shadow public boolean fancyGraphics;

    @Shadow public int ao;

    @Shadow public List<String> resourcePacks;

    @Shadow public List<String> incompatibleResourcePacks;

    @Shadow public boolean chatColor;

    @Shadow public boolean chatLink;

    @Shadow public boolean chatLinkPrompt;

    @Shadow public float chatOpacity;

    @Shadow public boolean snopperEnabled;

    @Shadow public boolean fullscreen;

    @Shadow public boolean vsync;

    @Shadow public boolean vbo;

    @Shadow public boolean alternativeBlocks;

    @Shadow public boolean reducedDebugInfo;

    @Shadow public boolean hideServerAddress;

    @Shadow public boolean advancedItemTooltips;

    @Shadow public boolean pauseOnLostFocus;

    @Shadow public int mipmapLevels;

    @Shadow public boolean entityShadows;

    @Shadow public KeyBinding forwardKey;

    @Shadow public KeyBinding leftKey;

    @Shadow public KeyBinding backKey;

    @Shadow public KeyBinding rightKey;

    @Shadow public KeyBinding jumpKey;

    @Shadow public KeyBinding sneakKey;

    @Shadow public KeyBinding sprintKey;

    @Shadow public KeyBinding inventoryKey;

    @Shadow public int particle;

    @Shadow public int guiScale;

    @Shadow public float saturation;

    @Shadow public float gamma;

    @Shadow public float fov;

    @Shadow public boolean smoothCameraEnabled;

    @Shadow public String lastServer;

    @Shadow public boolean debugProfilerEnabled;

    @Shadow public boolean debugFpsEnabled;

    @Shadow public boolean debugEnabled;

    @Shadow public int perspective;

    @Shadow public boolean hudHidden;

    @Shadow public KeyBinding fullscreenKey;

    @Shadow public KeyBinding smoothCameraKey;

    @Shadow public KeyBinding togglePerspectiveKey;

    @Shadow public KeyBinding screenshotKey;

    @Shadow public KeyBinding commandKey;

    @Shadow public KeyBinding playerListKey;

    @Shadow public KeyBinding chatKey;

    @Shadow public KeyBinding pickItemKey;

    @Shadow public KeyBinding attackKey;

    @Shadow public KeyBinding dropKey;

    @Shadow public KeyBinding useKey;

    @Override
    public float essence$sensitivity() {
        return sensitivity;
    }

    @Override
    public void essence$sensitivity(float v) {
        sensitivity = v;
    }

    @Override
    public int essence$viewDistance() {
        return viewDistance;
    }

    @Override
    public void essence$viewDistance(int i) {
        viewDistance = i;
    }

    @Override
    public boolean essence$bobView() {
        return bobView;
    }

    @Override
    public void essence$bobView(boolean b) {
        bobView = b;
    }

    @Override
    public boolean essence$fbo() {
        return fbo;
    }

    @Override
    public void essence$fbo(boolean b) {
        fbo = b;
    }

    @Override
    public int essence$maxFramerate() {
        return maxFramerate;
    }

    @Override
    public void essence$maxFramerate(int i) {
        maxFramerate = i;
    }

    @Override
    public boolean essence$fancyGraphics() {
        return fancyGraphics;
    }

    @Override
    public void essence$fancyGraphics(boolean b) {
        fancyGraphics = b;
    }

    @Override
    public int essence$ao() {
        return ao;
    }

    @Override
    public void essence$ao(int i) {
        ao = i;
    }

    @Override
    public @NotNull List<String> essence$resourcePacks() {
        return resourcePacks;
    }

    @Override
    public void essence$resourcePacks(@NotNull List<String> strings) {
        resourcePacks = strings;
    }

    @Override
    public @NotNull List<String> essence$incompatibleResourcePacks() {
        return incompatibleResourcePacks;
    }

    @Override
    public void essence$incompatibleResourcePacks(@NotNull List<String> strings) {
        incompatibleResourcePacks = strings;
    }

    @Override
    public boolean essence$fullscreen() {
        return fullscreen;
    }

    @Override
    public void essence$fullscreen(boolean b) {
        fullscreen = b;
    }

    @Override
    public boolean essence$vsync() {
        return vsync;
    }

    @Override
    public void essence$vsync(boolean b) {
        vsync = b;
    }

    @Override
    public boolean essence$vbo() {
        return vbo;
    }

    @Override
    public void essence$vbo(boolean b) {
        vbo = b;
    }

    @Override
    public boolean essence$advancedItemTooltips() {
        return advancedItemTooltips;
    }

    @Override
    public void essence$advancedItemTooltips(boolean b) {
        advancedItemTooltips = b;
    }

    @Override
    public boolean essence$pauseOnLostFocus() {
        return pauseOnLostFocus;
    }

    @Override
    public void essence$pauseOnLostFocus(boolean b) {
        pauseOnLostFocus = b;
    }

    @Override
    public int essence$mipmapLevels() {
        return mipmapLevels;
    }

    @Override
    public void essence$mipmapLevels(int i) {
        mipmapLevels = i;
    }

    @Override
    public boolean essence$entityShadows() {
        return entityShadows;
    }

    @Override
    public void essence$entityShadows(boolean b) {
        entityShadows = b;
    }

    @Override
    public @NotNull KeyBind essence$forwardKey() {
        return (KeyBind) forwardKey;
    }

    @Override
    public void essence$forwardKey(@NotNull KeyBind keyBind) {
        forwardKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$leftKey() {
        return (KeyBind) leftKey;
    }

    @Override
    public void essence$leftKey(@NotNull KeyBind keyBind) {
        leftKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$backKey() {
        return (KeyBind) backKey;
    }

    @Override
    public void essence$backKey(@NotNull KeyBind keyBind) {
        backKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$rightKey() {
        return (KeyBind) rightKey;
    }

    @Override
    public void essence$rightKey(@NotNull KeyBind keyBind) {
        rightKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$jumpKey() {
        return (KeyBind) jumpKey;
    }

    @Override
    public void essence$jumpKey(@NotNull KeyBind keyBind) {
        jumpKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$sneakKey() {
        return (KeyBind) sneakKey;
    }

    @Override
    public void essence$sneakKey(@NotNull KeyBind keyBind) {
        sneakKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$sprintKey() {
        return (KeyBind) sprintKey;
    }

    @Override
    public void essence$sprintKey(@NotNull KeyBind keyBind) {
        sprintKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$inventoryKey() {
        return (KeyBind) inventoryKey;
    }

    @Override
    public void essence$inventoryKey(@NotNull KeyBind keyBind) {
        inventoryKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$useKey() {
        return (KeyBind) useKey;
    }

    @Override
    public void essence$useKey(@NotNull KeyBind keyBind) {
        useKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$dropKey() {
        return (KeyBind) dropKey;
    }

    @Override
    public void essence$dropKey(@NotNull KeyBind keyBind) {
        dropKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$attackKey() {
        return (KeyBind) attackKey;
    }

    @Override
    public void essence$attackKey(@NotNull KeyBind keyBind) {
        attackKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$pickItemKey() {
        return (KeyBind) pickItemKey;
    }

    @Override
    public void essence$pickItemKey(@NotNull KeyBind keyBind) {
        pickItemKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$chatKey() {
        return (KeyBind) chatKey;
    }

    @Override
    public void essence$chatKey(@NotNull KeyBind keyBind) {
        chatKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$playerListKey() {
        return (KeyBind) playerListKey;
    }

    @Override
    public void essence$playerListKey(@NotNull KeyBind keyBind) {
        playerListKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$commandKey() {
        return (KeyBind) commandKey;
    }

    @Override
    public void essence$commandKey(@NotNull KeyBind keyBind) {
        commandKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$screenshotKey() {
        return (KeyBind) screenshotKey;
    }

    @Override
    public void essence$screenshotKey(@NotNull KeyBind keyBind) {
        screenshotKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$togglePerspectiveKey() {
        return (KeyBind) togglePerspectiveKey;
    }

    @Override
    public void essence$togglePerspectiveKey(@NotNull KeyBind keyBind) {
        togglePerspectiveKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$smoothCameraKey() {
        return (KeyBind) smoothCameraKey;
    }

    @Override
    public void essence$smoothCameraKey(@NotNull KeyBind keyBind) {
        smoothCameraKey = (KeyBinding) keyBind;
    }

    @Override
    public @NotNull KeyBind essence$fullscreenKey() {
        return (KeyBind) fullscreenKey;
    }

    @Override
    public void essence$fullscreenKey(@NotNull KeyBind keyBind) {
        fullscreenKey = (KeyBinding) keyBind;
    }

    @Override
    public boolean essence$hudHidden() {
        return hudHidden;
    }

    @Override
    public void essence$hudHidden(boolean b) {
        hudHidden = b;
    }

    @Override
    public int essence$perspective() {
        return perspective;
    }

    @Override
    public void essence$perspective(int i) {
        perspective = i;
    }

    @Override
    public boolean essence$debugEnabled() {
        return debugEnabled;
    }

    @Override
    public void essence$debugEnabled(boolean b) {
        debugEnabled = b;
    }

    @Override
    public boolean essence$debugProfilerEnabled() {
        return debugProfilerEnabled;
    }

    @Override
    public void essence$debugProfilerEnabled(boolean b) {
        debugProfilerEnabled = b;
    }

    @Override
    public boolean essence$debugFpsEnabled() {
        return debugFpsEnabled;
    }

    @Override
    public void essence$debugFpsEnabled(boolean b) {
        debugFpsEnabled = b;
    }

    @Override
    public @NotNull String essence$lastServer() {
        return lastServer;
    }

    @Override
    public void essence$lastServer(@NotNull String s) {
        lastServer = s;
    }

    @Override
    public boolean essence$smoothCameraEnabled() {
        return smoothCameraEnabled;
    }

    @Override
    public void essence$smoothCameraEnabled(boolean b) {
        smoothCameraEnabled = b;
    }

    @Override
    public float essence$fov() {
        return fov;
    }

    @Override
    public void essence$fov(float v) {
        fov = v;
    }

    @Override
    public float essence$gamma() {
        return gamma;
    }

    @Override
    public void essence$gamma(float v) {
        gamma = v;
    }

    @Override
    public float essence$saturation() {
        return saturation;
    }

    @Override
    public void essence$saturation(float v) {
        saturation = v;
    }

    @Override
    public int essence$guiScale() {
        return guiScale;
    }

    @Override
    public void essence$guiScale(int i) {
        guiScale = i;
    }

    @Override
    public int essence$particle() {
        return particle;
    }

    @Override
    public void essence$particle(int i) {
        particle = i;
    }
}
