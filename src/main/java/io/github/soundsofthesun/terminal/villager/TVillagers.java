package io.github.soundsofthesun.terminal.villager;


import com.google.common.collect.ImmutableSet;
import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.block.TBlocks;
import io.github.soundsofthesun.terminal.item.TItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class TVillagers {

    public static final ResourceKey<PoiType> TERMINAL_POI_KEY = registerPoiKey("terminal_poi");
    public static final PoiType TERMINAL_POI = registerPoi("terminal_poi", TBlocks.CONDUCTOR_STATION_BLOCK);

    public static final ResourceKey<VillagerProfession> CONDUCTOR_KEY = ResourceKey.create(
            Registries.VILLAGER_PROFESSION, ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, "conductor")
    );
    //TODO real texture for conductor villager
    public static final VillagerProfession CONDUCTOR = registerProfession("conductor", TERMINAL_POI_KEY);

    private static VillagerProfession registerProfession(String name, ResourceKey<PoiType> type) {
        return Registry.register(
                BuiltInRegistries.VILLAGER_PROFESSION,
                ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, name),
                new VillagerProfession(
                        Component.translatable("profession.terminal."+name),
                        holder -> holder.is(type),
                        holder -> holder.is(type),
                        ImmutableSet.of(Items.EMERALD),
                        ImmutableSet.of(TBlocks.CONDUCTOR_STATION_BLOCK),
                        SoundEvents.VILLAGER_WORK_TOOLSMITH
                )
        );
    }

    private static PoiType registerPoi(String name, Block block) {
        return PointOfInterestHelper.register(ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, name),
                1, 1, block);
    }

    private static ResourceKey<PoiType> registerPoiKey(String name) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, name));
    }

    public static void initialize() {
        //TODO more trades for conductor villager
        TradeOfferHelper.registerVillagerOffers(
                CONDUCTOR_KEY,
                1,
                factories -> {
                    factories.add((entity, random) -> new MerchantOffer(
                            new ItemCost(Items.EMERALD, 4),
                            Optional.of(new ItemCost(TItems.TERMINAL_CONTROLLER, 1)),
                            TItems.TERMINAL_CONTROLLER.getDefaultInstance(),
                            0,
                            8,
                            1,
                            0.04F
                    ));
                }
        );
    }
}
