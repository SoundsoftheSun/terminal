package io.github.soundsofthesun.terminal.client.data;

import io.github.soundsofthesun.terminal.item.TItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class TModelProvider extends FabricModelProvider {
    public TModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        //blockStateModelGenerator.registerSimpleCubeAll(TBlocks.TERMINAL_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(TItems.TERMINAL_CONTROLLER, ModelTemplates.FLAT_ITEM);
    }
}
