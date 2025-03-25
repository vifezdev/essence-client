package cc.essence.client.mixins.client.world;

import cc.essence.abstraction.client.entity.Entity;
import cc.essence.abstraction.client.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(net.minecraft.world.World.class)
public class MixinWorld implements World {

    @Shadow @Final public List<net.minecraft.entity.Entity> loadedEntities;

    @Override
    public @NotNull List<Entity> essence$loadedEntities() {
        return loadedEntities.stream().map(e -> (Entity) e).collect(Collectors.toList());
    }
}
