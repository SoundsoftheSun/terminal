package io.github.soundsofthesun.terminal.block.switchblock;

import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.attachment.TransitAttachedData;
import io.github.soundsofthesun.terminal.block.TBlockEntities;
import io.github.soundsofthesun.terminal.block.properties.TProperties;
import io.github.soundsofthesun.terminal.util.TUtil;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.AABB;

public class SwitchBlockEntity extends BlockEntity {

    public AABB box;
    public BlockPos checkForward;
    public BlockPos checkLeft;
    public BlockPos checkRight;
    public BlockPos target;
    public Direction facing;
    public Direction left;
    public Direction right;

    public SwitchBlockEntity(BlockPos pos, BlockState state) {
        super(TBlockEntities.SWITCH_BLOCK_ENTITY, pos, state);
        this.facing = state.getValue(HorizontalDirectionalBlock.FACING);
        this.left = facing;
        this.right = facing;
        if (facing==Direction.NORTH) {
            this.left = Direction.WEST;
            this.right = Direction.EAST;
        } else if (facing==Direction.SOUTH) {
            this.left = Direction.EAST;
            this.right = Direction.WEST;
        } else if (facing==Direction.WEST) {
            this.left = Direction.SOUTH;
            this.right = Direction.NORTH;
        } else if (facing==Direction.EAST) {
            this.left = Direction.NORTH;
            this.right = Direction.SOUTH;
        }


        this.target = pos.relative(this.facing);
        this.checkForward = target.relative(this.facing, 2);
        this.checkLeft = target.relative(this.left, 2);
        this.checkRight = target.relative(this.right, 2);
        this.box = new AABB(target).inflate(1);
    }

    int i = 0;

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, SwitchBlockEntity blockEntity) {
        if (!(world instanceof ServerLevel serverWorld)) return;

        if (blockEntity.i <= 2) {
            blockEntity.i++;
            return;
        }

        if (serverWorld.getBlockState(blockPos).getValue(TProperties.ACTIVE_PROPERTY) == TProperties.ACTIVE_STATE.ACTIVE) {
            serverWorld.setBlockAndUpdate(blockPos, blockState.setValue(TProperties.ACTIVE_PROPERTY, TProperties.ACTIVE_STATE.INACTIVE));
        }

        serverWorld.getEntitiesOfClass(AbstractMinecart.class, blockEntity.box, EntitySelector.ENTITY_STILL_ALIVE).forEach(entity -> {
            Entity passenger = entity.getFirstPassenger();
            if (!(passenger instanceof Player player)) return;
            TransitAttachedData data = player.getAttachedOrElse(TAttachmentTypes.TRANSIT_ATTACHMENT_TYPE, TransitAttachedData.DEFAULT);
            List<BlockPos> posList = data.get();
            if (posList.isEmpty()) return;

            boolean goFacing = posList.contains(blockEntity.checkForward);
            boolean goLeft = posList.contains(blockEntity.checkLeft);
            boolean goRight = posList.contains(blockEntity.checkRight);
            RailShape railShape = null;
            if (goFacing && goLeft) {
                railShape = TUtil.getRailShape(blockEntity.facing, blockEntity.left);
            } else if (goFacing && goRight) {
                railShape = TUtil.getRailShape(blockEntity.facing, blockEntity.right);
            } else if (goLeft && goRight) {
                railShape = TUtil.getRailShape(blockEntity.right, blockEntity.left);
            }
            if (!Objects.isNull(railShape)) {
                BlockState bs = serverWorld.getBlockState(blockEntity.target);
                if (bs.is(TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("rails")))) {
                    if (railShape == serverWorld.getBlockState(blockEntity.target).getValue(BlockStateProperties.RAIL_SHAPE)) return;
                    serverWorld.setBlockAndUpdate(blockPos, blockState.setValue(TProperties.ACTIVE_PROPERTY, TProperties.ACTIVE_STATE.ACTIVE));
                    serverWorld.setBlockAndUpdate(blockEntity.target, bs.setValue(RailBlock.SHAPE, railShape));
                    world.playSound(null, blockPos, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.PLAYERS, 2F, 1F);
                }
            }
        });
        blockEntity.i = 0;
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState oldState) {
        super.preRemoveSideEffects(pos, oldState);
    }
}
