package cc.essence.client.mixins.client.entity;

import cc.essence.abstraction.client.entity.LivingEntity;
import cc.essence.core.CoreKt;
import cc.essence.core.event.EventEntityHurts;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.entity.LivingEntity.class)
public abstract class MixinLivingEntity extends MixinEntity implements LivingEntity {
    @Shadow public abstract float getHealth();

    @Shadow public abstract float getMaxHealth();

    @Shadow public abstract boolean isAlive();

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;getAttacker()Lnet/minecraft/entity/Entity;", shift = At.Shift.AFTER))
    private void beforeDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        CoreKt.getBus().post(new EventEntityHurts(this, (LivingEntity) source.getAttacker(), amount));
    }


    @Override
    public float essence$getHealth() {
        return getHealth();
    }

    @Override
    public float essence$getMaxHealth() {
        return getMaxHealth();
    }

    @Override
    public boolean essence$isAlive() {
        return isAlive();
    }
}
