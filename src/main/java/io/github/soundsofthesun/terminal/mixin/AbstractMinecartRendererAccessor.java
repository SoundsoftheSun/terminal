package io.github.soundsofthesun.terminal.mixin;

import net.minecraft.client.renderer.entity.AbstractMinecartRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractMinecartRenderer.class)
public interface AbstractMinecartRendererAccessor {
    @Accessor("MINECART_LOCATION")
    static ResourceLocation getMinecartLocation() {
        throw new AssertionError();
    }

    @Accessor("MINECART_LOCATION")
    @Mutable
    static void setMinecartLocation(ResourceLocation location) {
        throw new AssertionError();
    }
}