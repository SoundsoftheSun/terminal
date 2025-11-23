package io.github.soundsofthesun.terminal.block;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.block.station.StationBlock;
import io.github.soundsofthesun.terminal.block.switchblock.SwitchBlock;
import io.github.soundsofthesun.terminal.block.terminalblock.TerminalBlock;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class TBlocks {
    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));
        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = keyOfItem(name);
            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey());
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }
        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Terminal.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Terminal.MOD_ID, name));
    }

    public static void initialize() {

        // Add to item groups
        ItemGroupEvents.modifyEntriesEvent(Terminal.TERMINAL_GROUP_KEY)
                .register((itemGroup) -> {
            itemGroup.add(TBlocks.STATION_BLOCK.asItem());
            itemGroup.add(TBlocks.SWITCH_BLOCK.asItem());
            itemGroup.add(TBlocks.TERMINAL_BLOCK.asItem());
        });

        // Tooltip translation keys
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.isOf(TBlocks.TERMINAL_BLOCK.asItem())) list.add(Text.translatable("blockTooltip.terminal.terminal_block"));
            if (itemStack.isOf(TBlocks.STATION_BLOCK.asItem())) list.add(Text.translatable("blockTooltip.terminal.station_block"));
            if (itemStack.isOf(TBlocks.SWITCH_BLOCK.asItem())) list.add(Text.translatable("blockTooltip.terminal.switch_block"));
        });

    }

    public static final Block STATION_BLOCK = register(
            "station_block",
            StationBlock::new,
            AbstractBlock.Settings.create()
                    .nonOpaque()
                    .mapColor(MapColor.GRAY)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.5F, 1200.0F)
            ,
            true
    );

    public static final Block SWITCH_BLOCK = register(
            "switch_block",
            SwitchBlock::new,
            AbstractBlock.Settings.create()
                    .nonOpaque()
                    .mapColor(MapColor.EMERALD_GREEN)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.5F, 1200.0F)
            ,
            true
    );

    public static final Block TERMINAL_BLOCK = register(
            "terminal_block",
            TerminalBlock::new,
            AbstractBlock.Settings.create()
                    .nonOpaque()
                    .mapColor(MapColor.PALE_PURPLE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .luminance(state -> 15)
                    .strength(3.0F, 1200.0F),
            true
    );

}
