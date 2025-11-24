package io.github.soundsofthesun.terminal.block.terminalblock;

import io.github.soundsofthesun.terminal.attachment.StationAttachedData;
import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.attachment.TerminalAttachedData;
import io.github.soundsofthesun.terminal.block.TBlockEntities;
import io.github.soundsofthesun.terminal.block.properties.TProperties;
import io.github.soundsofthesun.terminal.block.station.StationBlockEntity;
import io.github.soundsofthesun.terminal.util.PathStations;
import java.util.LinkedHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TerminalBlockEntity extends BlockEntity {
    public TerminalBlockEntity(BlockPos pos, BlockState state) {
        super(TBlockEntities.TERMINAL_BLOCK_ENTITY, pos, state);
    }

    PathStations path = null;
    StationAttachedData data = null;
    int i = 0;

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, TerminalBlockEntity be) {
        if (!(world instanceof ServerLevel serverWorld)) return;

        if (be.i <= 10) {
            be.i++;
            return;
        }

        if (blockState.getValue(TProperties.ACTIVE_PROPERTY) == TProperties.ACTIVE_STATE.ACTIVE) {

            world.playSound(null, blockPos, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1F, 1F);

            if (be.data == null) be.data = serverWorld.getAttachedOrCreate(TAttachmentTypes.STATION_ATTACHMENT_TYPE);
            if (be.path == null) be.path = new PathStations(serverWorld);
            be.path.getNetwork(blockPos);
            LinkedHashMap<Long, String> newMap = new LinkedHashMap<>();
            System.out.println("TERMSIZE START");
            if (be.path.terminals.size() > 1) {
                be.path.terminals.forEach(System.out::println);
                System.out.println(blockPos);
                System.out.println(be.path.terminals.remove(blockPos));
                serverWorld.setBlockAndUpdate(blockPos, blockState.setValue(TProperties.LIGHT_PROPERTY, TProperties.LIGHT_STATE.OFF).setValue(TProperties.ACTIVE_PROPERTY, TProperties.ACTIVE_STATE.INACTIVE));
                return;
            }
            if (be.path.networkStations.size() < 500) {
                be.path.networkStations.forEach(pos -> {
                    if (serverWorld.getBlockEntity(pos) instanceof StationBlockEntity sbe) {
                        newMap.put(pos.asLong(), sbe.getAttachedOrCreate(TAttachmentTypes.NAME_ATTACHED_DATA).name());
                        sbe.setAttached(TAttachmentTypes.TERMINAL_ATTACHED_DATA, new TerminalAttachedData(blockPos));
                        world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(TProperties.LIGHT_PROPERTY, TProperties.LIGHT_STATE.GREEN));
                    }
                });
                be.setAttached(TAttachmentTypes.STATION_ATTACHMENT_TYPE, new StationAttachedData(newMap));
            } else {
                be.path.networkStations.forEach(pos -> {
                    world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(TProperties.LIGHT_PROPERTY, TProperties.LIGHT_STATE.RED));
                });
            }
            serverWorld.setBlockAndUpdate(blockPos, blockState.setValue(TProperties.LIGHT_PROPERTY, TProperties.LIGHT_STATE.GREEN).setValue(TProperties.ACTIVE_PROPERTY, TProperties.ACTIVE_STATE.INACTIVE));
        }
        be.i = 0;
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState oldState) {
        super.preRemoveSideEffects(pos, oldState);
    }

}
