package io.github.soundsofthesun.terminal.entity.goldminecart;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.mixin.AbstractMinecartRendererAccessor;
import net.minecraft.client.renderer.entity.AbstractMinecartRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

public class GoldMinecartRenderer extends AbstractMinecartRenderer<AbstractMinecart, GoldMinecartRenderState> {

    public GoldMinecartRenderer(EntityRendererProvider.Context context) {
        super(context, GoldMinecartModel.GOLD_MINECART_LOCATION);
        ResourceLocation VANILLA_MINECART = ResourceLocation.withDefaultNamespace("textures/entity/minecart.png");
        ResourceLocation GOLD_MINECART = ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, "textures/entity/gold_minecart.png");
        AbstractMinecartRendererAccessor.setMinecartLocation(VANILLA_MINECART);

    }

    @Override
    public GoldMinecartRenderState createRenderState() {
        return new GoldMinecartRenderState();
    }


}
