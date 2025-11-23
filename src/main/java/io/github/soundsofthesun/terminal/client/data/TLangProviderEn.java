package io.github.soundsofthesun.terminal.client.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import java.util.concurrent.CompletableFuture;

public class TLangProviderEn extends FabricLanguageProvider {
    public TLangProviderEn(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider wrapperLookup, TranslationBuilder b) {

        // Config Translations
        b.add("text.config.terminal-cfg.title", "Terminal Config");
        b.add("text.config.terminal-cfg.option.max_network_size", "Maximum Network Size (Blocks)");

        // Mod Translations
        b.add("terminal_group.terminal", "Terminal");

        b.add("item.terminal.terminal_controller", "Terminal Controller");
        b.add("itemTooltip.terminal.terminal_controller", "Used to craft Terminal Items.");

        b.add("block.terminal.station_block", "Station Block");
        b.add("blockTooltip.terminal.station_block", "Creates a destination on a rail network.");

        b.add("block.terminal.switch_block", "Switch Block");
        b.add("blockTooltip.terminal.switch_block", "Creates a junction on a rail network.");

        b.add("block.terminal.terminal_block", "Terminal Block");
        b.add("blockTooltip.terminal.terminal_block", "Creates a rail network.");

        b.add("block.terminal.conductor_station_block", "Conductor Station");
        b.add("blockTooltip.terminal.conductor_station_block", "Workstation for Conductor Villagers!");

        // Villagers
        b.add("profession.terminal.conductor", "Conductor");


    }
}
