package cc.essence.client.mixins.client.gl;

import cc.essence.abstraction.client.gl.Framebuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.client.gl.Framebuffer.class)
public abstract class MixinFramebuffer implements Framebuffer {
    @Shadow public int fbo;

    @Shadow public int colorAttachment;

    @Shadow public int viewportWidth;

    @Shadow public int viewportHeight;

    @Shadow public int textureWidth;

    @Shadow public int textureHeight;

    @Shadow public abstract void clear();

    @Shadow public abstract void draw(int width, int height);

    @Shadow public abstract void bind(boolean viewPort);

    @Shadow public abstract void unbind();

    @Shadow public abstract void beginRead();

    @Shadow public abstract void endRead();

    @Shadow public abstract void delete();

    @Shadow public abstract void resize(int width, int height);

    @Override
    public int getId() {
        return fbo;
    }

    @Override
    public int getTexture() {
        return colorAttachment;
    }

    @Override
    public int getWidth() {
        return viewportWidth;
    }

    @Override
    public int getHeight() {
        return viewportHeight;
    }

    @Override
    public int getTexWidth() {
        return textureWidth;
    }

    @Override
    public int getTexHeight() {
        return textureHeight;
    }

    @Override
    public void clearFbo() {
        clear();
    }

    @Override
    public void drawFbo(int w, int h) {
        draw(w, h);
    }

    @Override
    public void resizeFbo(int w, int h) {
        this.resize(w, h);
    }

    @Override
    public void bindFbo(boolean viewport) {
        bind(viewport);
    }

    @Override
    public void unbindFbo() {
        unbind();
    }

    @Override
    public void bindFboTexture() {
        beginRead();
    }

    @Override
    public void unbindFboTexture() {
        endRead();
    }

    @Override
    public void deleteFbo() {
        delete();
    }
}
