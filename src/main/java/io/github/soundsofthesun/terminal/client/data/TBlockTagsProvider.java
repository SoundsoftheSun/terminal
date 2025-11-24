package io.github.soundsofthesun.terminal.client.data;

import io.github.soundsofthesun.terminal.block.TBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class TBlockTagsProvider extends FabricTagProvider.BlockTagProvider {
    public TBlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(TBlocks.TERMINAL_BLOCK)
                .add(TBlocks.STATION_BLOCK)
                .add(TBlocks.SWITCH_BLOCK)
                .setReplace(true);

        valueLookupBuilder(BlockTags.MINEABLE_WITH_AXE)
                .add(TBlocks.CONDUCTOR_STATION_BLOCK)
                .setReplace(true);

    }
}
