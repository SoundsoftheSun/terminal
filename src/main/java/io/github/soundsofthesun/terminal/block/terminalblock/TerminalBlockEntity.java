package io.github.soundsofthesun.terminal.block.terminalblock;

import io.github.soundsofthesun.terminal.attachment.StationAttachedData;
import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.attachment.TerminalAttachedData;
import io.github.soundsofthesun.terminal.block.TBlockEntities;
import io.github.soundsofthesun.terminal.block.properties.TProperties;
import io.github.soundsofthesun.terminal.block.station.StationBlockEntity;
import io.github.soundsofthesun.terminal.util.PathStations;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedHashMap;

public class TerminalBlockEntity extends BlockEntity {
    public TerminalBlockEntity(BlockPos pos, BlockState state) {
        super(TBlockEntities.TERMINAL_BLOCK_ENTITY, pos, state);
    }

    PathStations path = null;
    StationAttachedData data = null;
    int i = 0;

    public static void tick(World world, BlockPos blockPos, BlockState blockState, TerminalBlockEntity be) {
        if (!(world instanceof ServerWorld serverWorld)) return;

        if (be.i <= 10) {
            be.i++;
            return;
        }

        if (blockState.get(TProperties.ACTIVE_PROPERTY) == TProperties.ACTIVE_STATE.ACTIVE) {

            world.playSound(null, blockPos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.PLAYERS, 1F, 1F);

            if (be.data == null) be.data = serverWorld.getAttachedOrCreate(TAttachmentTypes.STATION_ATTACHMENT_TYPE);
            if (be.path == null) be.path = new PathStations(serverWorld);
            be.path.getNetwork(blockPos);
            LinkedHashMap<Long, String> newMap = new LinkedHashMap<>();
            System.out.println("TERMSIZE START");
            if (be.path.terminals.size() > 1) {
                be.path.terminals.forEach(System.out::println);
                System.out.println(blockPos);
                System.out.println(be.path.terminals.remove(blockPos));
                serverWorld.setBlockState(blockPos, blockState.with(TProperties.LIGHT_PROPERTY, TProperties.LIGHT_STATE.OFF).with(TProperties.ACTIVE_PROPERTY, TProperties.ACTIVE_STATE.INACTIVE));
                return;
            }
            if (be.path.networkStations.size() < 500) {
                be.path.networkStations.forEach(pos -> {
                    if (serverWorld.getBlockEntity(pos) instanceof StationBlockEntity sbe) {
                        newMap.put(pos.asLong(), sbe.getAttachedOrCreate(TAttachmentTypes.NAME_ATTACHED_DATA).name());
                        sbe.setAttached(TAttachmentTypes.TERMINAL_ATTACHED_DATA, new TerminalAttachedData(blockPos));
                        world.setBlockState(pos, world.getBlockState(pos).with(TProperties.LIGHT_PROPERTY, TProperties.LIGHT_STATE.PENDING));
                    }
                });
                be.setAttached(TAttachmentTypes.STATION_ATTACHMENT_TYPE, new StationAttachedData(newMap));
            } else {
                be.path.networkStations.forEach(pos -> {
                    world.setBlockState(pos, world.getBlockState(pos).with(TProperties.LIGHT_PROPERTY, TProperties.LIGHT_STATE.RED));
                });
            }
            serverWorld.setBlockState(blockPos, blockState.with(TProperties.LIGHT_PROPERTY, TProperties.LIGHT_STATE.GREEN).with(TProperties.ACTIVE_PROPERTY, TProperties.ACTIVE_STATE.INACTIVE));
        }
        be.i = 0;
    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        super.onBlockReplaced(pos, oldState);//TODO
    }

}
