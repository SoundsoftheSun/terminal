package io.github.soundsofthesun.terminal;

import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.block.TBlockEntities;
import io.github.soundsofthesun.terminal.block.TBlocks;
import io.github.soundsofthesun.terminal.item.TItems;
import io.github.soundsofthesun.terminal.network.TPayloads;
import io.github.soundsofthesun.terminal.particle.TParticles;
import io.github.soundsofthesun.terminal.villager.TVillagers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Terminal implements ModInitializer {
    // Where are my functions?

    public static final String MOD_ID = "terminal";
    public static final Logger log = LoggerFactory.getLogger(MOD_ID);
    public static final io.github.soundsofthesun.terminal.TerminalCfg CONFIG = io.github.soundsofthesun.terminal.TerminalCfg.createAndLoad();

    // Item Group
    public static final ResourceKey<CreativeModeTab> TERMINAL_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, "terminal_group"));
    public static final CreativeModeTab TERMINAL_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(TItems.TERMINAL_CONTROLLER))
            .title(Component.translatable("terminal_group.terminal"))
            .build();

    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TERMINAL_GROUP_KEY, TERMINAL_GROUP);

        TItems.initialize();
        TBlocks.initialize();
        TBlockEntities.initialize();
        TAttachmentTypes.initialize();
        TPayloads.initialize();
        TParticles.initialize();
        TVillagers.initialize();
    }
}
