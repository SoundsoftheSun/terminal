package io.github.soundsofthesun.terminal.entity;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.entity.goldminecart.GoldMinecart;
import io.github.soundsofthesun.terminal.entity.goldminecart.GoldMinecartModel;
import io.github.soundsofthesun.terminal.entity.goldminecart.GoldMinecartRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.AbstractMinecartRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnGroupData;

public class TEntities {

    public static final EntityType<GoldMinecart> GOLD_MINECART = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, "gold_minecart"),
            EntityType.Builder.<GoldMinecart>of(GoldMinecart::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .build(ResourceKey.create(
                            Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, "gold_minecart")))
    );

    public static void initialize() {}

    public static void initializeClient() {
        EntityModelLayerRegistry.registerModelLayer(GoldMinecartModel.GOLD_MINECART_LOCATION, GoldMinecartModel::createBodyLayer);
        EntityRenderers.register(GOLD_MINECART, GoldMinecartRenderer::new);
    }

}
