package cc.essence.client.mixins.client.entity;

import cc.essence.abstraction.client.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.entity.Entity.class)
public abstract class MixinEntity implements Entity {
    @Shadow public double x;

    @Shadow public double y;

    @Shadow public double z;

    @Shadow public double velocityX;

    @Shadow public double velocityY;

    @Shadow public double velocityZ;

    @Shadow public float yaw;

    @Shadow public float pitch;

    @Shadow public boolean onGround;

    @Shadow private int entityId;

    @Override
    public int essence$id() {
        return entityId;
    }

    @Override
    public double essence$x() {
        return x;
    }

    @Override
    public double essence$y() {
        return y;
    }

    @Override
    public double essence$z() {
        return z;
    }

    @Override
    public double essence$velocityX() {
        return velocityX;
    }

    @Override
    public double essence$velocityY() {
        return velocityY;
    }

    @Override
    public double essence$velocityZ() {
        return velocityZ;
    }

    @Override
    public float essence$yaw() {
        return yaw;
    }

    @Override
    public float essence$pitch() {
        return pitch;
    }

    @Override
    public boolean essence$onGround() {
        return onGround;
    }
}
