package io.github.soundsofthesun.terminal.client.data;

import io.github.soundsofthesun.terminal.block.TBlocks;
import io.github.soundsofthesun.terminal.item.TItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class TRecipeProvider extends FabricRecipeProvider {
    public TRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

                // terminal controller
                createShaped(RecipeCategory.TRANSPORTATION, TItems.TERMINAL_CONTROLLER, 1)
                        .pattern("glg")
                        .pattern("gtg")
                        .pattern("ggg")
                        .input('g', Items.GOLD_NUGGET)
                        .input('t', Items.TINTED_GLASS)
                        .input('l', Items.LIGHTNING_ROD)
                        .group("terminal")
                        .criterion(hasItem(Items.LIGHTNING_ROD), conditionsFromItem(Items.LIGHTNING_ROD))
                        .offerTo(exporter);

                // switch block
                createShapeless(RecipeCategory.TRANSPORTATION, TBlocks.SWITCH_BLOCK, 1)
                        .input(Items.COMPARATOR)
                        .input(Items.PISTON)
                        .input(TItems.TERMINAL_CONTROLLER)
                        .group("terminal")
                        .criterion(hasItem(TItems.TERMINAL_CONTROLLER), conditionsFromItem(TItems.TERMINAL_CONTROLLER))
                        .offerTo(exporter);

                // station block
                createShaped(RecipeCategory.TRANSPORTATION, TBlocks.STATION_BLOCK, 1)
                        .pattern("ggg")
                        .pattern("olo")
                        .pattern("oco")
                        .input('g', Items.GLASS)
                        .input('o', Items.OBSIDIAN)
                        .input('l', Items.REDSTONE_LAMP)
                        .input('c', TItems.TERMINAL_CONTROLLER)
                        .group("terminal")
                        .criterion(hasItem(TItems.TERMINAL_CONTROLLER), conditionsFromItem(TItems.TERMINAL_CONTROLLER))
                        .offerTo(exporter);

                // terminal block
                createShaped(RecipeCategory.TRANSPORTATION, TBlocks.TERMINAL_BLOCK, 1)
                        .pattern("ogo")
                        .pattern("gtg")
                        .pattern("ogo")
                        .input('o', Items.OBSIDIAN)
                        .input('g', Items.TINTED_GLASS)
                        .input('t', TItems.TERMINAL_CONTROLLER)
                        .group("terminal")
                        .criterion(hasItem(TItems.TERMINAL_CONTROLLER), conditionsFromItem(TItems.TERMINAL_CONTROLLER))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "Terminal Recipes";
    }
}
