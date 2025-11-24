package io.github.soundsofthesun.terminal.client.data;

import io.github.soundsofthesun.terminal.block.TBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class TBlockLootTableProvider extends FabricBlockLootTableProvider {
    public TBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(TBlocks.STATION_BLOCK);
        dropSelf(TBlocks.CONDUCTOR_STATION_BLOCK);
        dropSelf(TBlocks.TERMINAL_BLOCK);
        dropSelf(TBlocks.SWITCH_BLOCK);
    }
}
