package io.github.soundsofthesun.terminal.block;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.block.station.StationBlockEntity;
import io.github.soundsofthesun.terminal.block.station.StationBlockEntityRenderer;
import io.github.soundsofthesun.terminal.block.switchblock.SwitchBlockEntity;
import io.github.soundsofthesun.terminal.block.switchblock.SwitchBlockEntityRenderer;
import io.github.soundsofthesun.terminal.block.terminalblock.TerminalBlockEntity;
import io.github.soundsofthesun.terminal.block.terminalblock.TerminalBlockEntityRenderer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TBlockEntities {

    public static void initialize() {
        StationBlockEntityRenderer.initialize();
        SwitchBlockEntityRenderer.initialize();
        TerminalBlockEntityRenderer.initialize();
    }

    public static final BlockEntityType<StationBlockEntity> STATION_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Terminal.MOD_ID, "station_block_entity"), FabricBlockEntityTypeBuilder.create(StationBlockEntity::new, TBlocks.STATION_BLOCK).build());

    public static final BlockEntityType<SwitchBlockEntity> SWITCH_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Terminal.MOD_ID, "switch_block_entity"), FabricBlockEntityTypeBuilder.create(SwitchBlockEntity::new, TBlocks.SWITCH_BLOCK).build());

    public static final BlockEntityType<TerminalBlockEntity> TERMINAL_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Terminal.MOD_ID, "terminal_block_entity"), FabricBlockEntityTypeBuilder.create(TerminalBlockEntity::new, TBlocks.TERMINAL_BLOCK).build());

}
