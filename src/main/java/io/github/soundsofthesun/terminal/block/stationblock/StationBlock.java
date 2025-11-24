package io.github.soundsofthesun.terminal.block.stationblock;

import com.mojang.serialization.MapCodec;
import io.github.soundsofthesun.terminal.attachment.StationAttachedData;
import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.attachment.TerminalAttachedData;
import io.github.soundsofthesun.terminal.block.TBlockEntities;
import io.github.soundsofthesun.terminal.block.properties.TProperties;
import io.github.soundsofthesun.terminal.block.terminalblock.TerminalBlockEntity;
import io.github.soundsofthesun.terminal.network.s2c.StationInteractS2CPayload;
import io.github.soundsofthesun.terminal.util.PathStations;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class StationBlock extends BaseEntityBlock implements EntityBlock { // TODO make yellow when a player is in transit on path
    public StationBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TProperties.LIGHT_PROPERTY, TProperties.LIGHT_STATE.OFF)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TProperties.LIGHT_PROPERTY);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.isClientSide()) return InteractionResult.SUCCESS;

        BlockEntity stationBlockEntity = world.getBlockEntity(pos);
        if (stationBlockEntity instanceof StationBlockEntity) {
            TerminalAttachedData terminalData = stationBlockEntity.getAttachedOrElse(TAttachmentTypes.TERMINAL_ATTACHED_DATA, TerminalAttachedData.DEFAULT);
            BlockEntity terminalBlockEntity = world.getBlockEntity(terminalData.terminal());
            if (terminalBlockEntity instanceof TerminalBlockEntity) {
                StationAttachedData stationData = terminalBlockEntity.getAttachedOrElse(TAttachmentTypes.STATION_ATTACHMENT_TYPE, StationAttachedData.DEFAULT);
                if (!stationData.posMap().isEmpty()) {
                    ServerPlayNetworking.send((ServerPlayer) player, new StationInteractS2CPayload(stationData, pos));
                }
            } else {
                PathStations path = new PathStations((ServerLevel) world);
                path.getNetwork(pos);
                if (!path.terminals.isEmpty()) {
                    BlockEntity newTerminalBlockEntity = world.getBlockEntity(path.terminals.iterator().next());
                    if (newTerminalBlockEntity instanceof TerminalBlockEntity) {
                        world.setBlockAndUpdate(newTerminalBlockEntity.getBlockPos(), world.getBlockState(newTerminalBlockEntity.getBlockPos()).setValue(TProperties.ACTIVE_PROPERTY, TProperties.ACTIVE_STATE.ACTIVE));
                    }
                } else {
                    path.networkStations.forEach(pos2 -> {
                        world.setBlockAndUpdate(pos2, world.getBlockState(pos2).setValue(TProperties.LIGHT_PROPERTY, TProperties.LIGHT_STATE.RED));
                        world.playSound(null, pos2, SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1F, 1F);
                    });
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(4, 0, 4, 12, 24, 12);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(StationBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StationBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, TBlockEntities.STATION_BLOCK_ENTITY, io.github.soundsofthesun.terminal.block.stationblock.StationBlockEntity::tick);
    }

}
