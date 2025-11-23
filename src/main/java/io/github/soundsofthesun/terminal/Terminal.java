package io.github.soundsofthesun.terminal;

import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.block.TBlockEntities;
import io.github.soundsofthesun.terminal.block.TBlocks;
import io.github.soundsofthesun.terminal.item.TItems;
import io.github.soundsofthesun.terminal.network.TPayloads;
import io.github.soundsofthesun.terminal.particle.TParticles;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Terminal implements ModInitializer {
    // Where are my functions?

    public static final String MOD_ID = "terminal";
    public static final Logger log = LoggerFactory.getLogger(MOD_ID);
    public static final io.github.soundsofthesun.terminal.TerminalCfg CONFIG = io.github.soundsofthesun.terminal.TerminalCfg.createAndLoad();

    // Item Group
    public static final RegistryKey<ItemGroup> TERMINAL_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(Terminal.MOD_ID, "terminal_group"));
    public static final ItemGroup TERMINAL_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(TItems.TERMINAL_CONTROLLER))
            .displayName(Text.translatable("terminal_group.terminal"))
            .build();

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM_GROUP, TERMINAL_GROUP_KEY, TERMINAL_GROUP);

        TItems.initialize();
        TBlocks.initialize();
        TBlockEntities.initialize();
        TAttachmentTypes.initialize();
        TPayloads.initialize();
        TParticles.initialize();
    }
}
