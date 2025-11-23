package io.github.soundsofthesun.terminal.block.station;

import com.mojang.serialization.MapCodec;
import io.github.soundsofthesun.terminal.attachment.StationAttachedData;
import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.attachment.TerminalAttachedData;
import io.github.soundsofthesun.terminal.block.TBlockEntities;
import io.github.soundsofthesun.terminal.block.properties.TProperties;
import io.github.soundsofthesun.terminal.block.terminalblock.TerminalBlockEntity;
import io.github.soundsofthesun.terminal.screen.StationScreen;
import io.github.soundsofthesun.terminal.util.PathStations;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StationBlock extends BlockWithEntity implements BlockEntityProvider {
    public StationBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(TProperties.LIGHT_PROPERTY, TProperties.LIGHT_STATE.OFF)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TProperties.LIGHT_PROPERTY);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        BlockEntity stationBlockEntity = world.getBlockEntity(pos);
        if (stationBlockEntity instanceof StationBlockEntity) {
            TerminalAttachedData terminalData = stationBlockEntity.getAttachedOrElse(TAttachmentTypes.TERMINAL_ATTACHED_DATA, TerminalAttachedData.DEFAULT);
            BlockEntity terminalBlockEntity = world.getBlockEntity(terminalData.terminal());
            if (terminalBlockEntity instanceof TerminalBlockEntity) {
                StationAttachedData stationData = terminalBlockEntity.getAttachedOrElse(TAttachmentTypes.STATION_ATTACHMENT_TYPE, StationAttachedData.DEFAULT);
                if (!stationData.posMap().isEmpty()) {
                    if (world.isClient()) {
                        StationScreen screen = new StationScreen(Text.literal("Title"), stationData.posMap(), pos);
                        MinecraftClient.getInstance().setScreen(screen);
                    }
                }
            } else {
                if (!world.isClient()) {
                    PathStations path = new PathStations((ServerWorld) world);
                    path.getNetwork(pos);
                    if (!path.terminals.isEmpty()) {
                        BlockEntity newTerminalBlockEntity = world.getBlockEntity(path.terminals.iterator().next());
                        if (newTerminalBlockEntity instanceof TerminalBlockEntity) {
                            world.setBlockState(newTerminalBlockEntity.getPos(), world.getBlockState(newTerminalBlockEntity.getPos()).with(TProperties.ACTIVE_PROPERTY, TProperties.ACTIVE_STATE.ACTIVE));
                        }
                    } else {
                        path.networkStations.forEach(pos2 -> {
                            world.setBlockState(pos2, world.getBlockState(pos2).with(TProperties.LIGHT_PROPERTY, TProperties.LIGHT_STATE.RED));
                            world.playSound(null, pos2, SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.PLAYERS, 1F, 1F);
                        });
                    }

                }
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(4, 0, 4, 12, 24, 12);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(StationBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StationBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, TBlockEntities.STATION_BLOCK_ENTITY, io.github.soundsofthesun.terminal.block.station.StationBlockEntity::tick);
    }

}
