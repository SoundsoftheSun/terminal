package io.github.soundsofthesun.terminal.block;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.block.conductorstation.ConductorStation;
import io.github.soundsofthesun.terminal.block.station.StationBlock;
import io.github.soundsofthesun.terminal.block.switchblock.SwitchBlock;
import io.github.soundsofthesun.terminal.block.terminalblock.TerminalBlock;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import java.util.function.Function;

public class TBlocks {
    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
        ResourceKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.setId(blockKey));
        if (shouldRegisterItem) {
            ResourceKey<Item> itemKey = keyOfItem(name);
            BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }
        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static ResourceKey<Block> keyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, name));
    }

    private static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, name));
    }

    public static void initialize() {

        // Add to item groups
        ItemGroupEvents.modifyEntriesEvent(Terminal.TERMINAL_GROUP_KEY)
                .register((itemGroup) -> {
            itemGroup.accept(TBlocks.STATION_BLOCK.asItem());
            itemGroup.accept(TBlocks.SWITCH_BLOCK.asItem());
            itemGroup.accept(TBlocks.TERMINAL_BLOCK.asItem());
            itemGroup.accept(TBlocks.CONDUCTOR_STATION_BLOCK.asItem());
        });
    }

    public static void initializeClient() {
        // Tooltip translation keys
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.is(TBlocks.TERMINAL_BLOCK.asItem())) list.add(Component.translatable("blockTooltip.terminal.terminal_block"));
            if (itemStack.is(TBlocks.STATION_BLOCK.asItem())) list.add(Component.translatable("blockTooltip.terminal.station_block"));
            if (itemStack.is(TBlocks.SWITCH_BLOCK.asItem())) list.add(Component.translatable("blockTooltip.terminal.switch_block"));
            if (itemStack.is(TBlocks.CONDUCTOR_STATION_BLOCK.asItem())) list.add(Component.translatable("blockTooltip.terminal.conductor_station_block"));
        });
    }

    public static final Block STATION_BLOCK = register(
            "station_block",
            StationBlock::new,
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .mapColor(MapColor.COLOR_GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(3F, 1200.0F)
            ,
            true
    );

    public static final Block SWITCH_BLOCK = register(
            "switch_block",
            SwitchBlock::new,
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .mapColor(MapColor.EMERALD)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F, 1200.0F)
            ,
            true
    );

    public static final Block TERMINAL_BLOCK = register(
            "terminal_block",
            TerminalBlock::new,
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .mapColor(MapColor.ICE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .lightLevel(state -> 15)
                    .requiresCorrectToolForDrops()
                    .strength(3F, 1200.0F),
            true
    );

    public static final Block CONDUCTOR_STATION_BLOCK = register(
            "conductor_station_block",
            ConductorStation::new,
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .mapColor(MapColor.COLOR_BLACK)
                    .instrument(NoteBlockInstrument.COW_BELL)
                    .requiresCorrectToolForDrops()
                    .strength(2.5F, 5F),
            true
    );

}
