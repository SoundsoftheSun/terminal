package io.github.soundsofthesun.terminal.entity.goldminecart;

import io.github.soundsofthesun.terminal.Terminal;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;

public class GoldMinecartModel extends EntityModel<GoldMinecartRenderState> {
    public GoldMinecartModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition createBodyLayer() {
        return MinecartModel.createBodyLayer();
    }

    public static final ModelLayerLocation GOLD_MINECART_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, "textures/entity/gold_minecart.png"), "main");

}
