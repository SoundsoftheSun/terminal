package io.github.soundsofthesun.terminal.client.data;

import io.github.soundsofthesun.terminal.block.TBlocks;
import io.github.soundsofthesun.terminal.item.TItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import java.util.concurrent.CompletableFuture;

public class TRecipeProvider extends FabricRecipeProvider {
    public TRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                // terminal controller
                shaped(RecipeCategory.TRANSPORTATION, TItems.TERMINAL_CONTROLLER, 1)
                        .pattern("glg")
                        .pattern("gtg")
                        .pattern("ggg")
                        .define('g', Items.GOLD_NUGGET)
                        .define('t', Items.TINTED_GLASS)
                        .define('l', Items.LIGHTNING_ROD)
                        .group("terminal")
                        .unlockedBy(getHasName(Items.RAIL), has(Items.RAIL))
                        .save(output);

                // switch block
                shapeless(RecipeCategory.TRANSPORTATION, TBlocks.SWITCH_BLOCK, 1)
                        .requires(Items.COMPARATOR)
                        .requires(Items.PISTON)
                        .requires(TItems.TERMINAL_CONTROLLER)
                        .group("terminal")
                        .unlockedBy(getHasName(TItems.TERMINAL_CONTROLLER), has(TItems.TERMINAL_CONTROLLER))
                        .save(output);

                // station block
                shaped(RecipeCategory.TRANSPORTATION, TBlocks.STATION_BLOCK, 1)
                        .pattern("ggg")
                        .pattern("olo")
                        .pattern("oco")
                        .define('g', Items.GLASS)
                        .define('o', Items.OBSIDIAN)
                        .define('l', Items.REDSTONE_LAMP)
                        .define('c', TItems.TERMINAL_CONTROLLER)
                        .group("terminal")
                        .unlockedBy(getHasName(TItems.TERMINAL_CONTROLLER), has(TItems.TERMINAL_CONTROLLER))
                        .save(output);

                // terminal block
                shaped(RecipeCategory.TRANSPORTATION, TBlocks.TERMINAL_BLOCK, 1)
                        .pattern("ogo")
                        .pattern("gtg")
                        .pattern("ogo")
                        .define('o', Items.OBSIDIAN)
                        .define('g', Items.TINTED_GLASS)
                        .define('t', TItems.TERMINAL_CONTROLLER)
                        .group("terminal")
                        .unlockedBy(getHasName(TItems.TERMINAL_CONTROLLER), has(TItems.TERMINAL_CONTROLLER))
                        .save(output);

                // conductor station
                shaped(RecipeCategory.TRANSPORTATION, TBlocks.CONDUCTOR_STATION_BLOCK, 1)
                        .pattern("rrr")
                        .pattern("ppp")
                        .pattern("ppp")
                        .define('r', ItemTags.RAILS)
                        .define('p', ItemTags.PLANKS)
                        .group("terminal")
                        .unlockedBy(getHasName(Items.RAIL), has(Items.RAIL))
                        .save(output);

            }
        };
    }

    @Override
    public String getName() {
        return "Terminal Recipes";
    }
}
