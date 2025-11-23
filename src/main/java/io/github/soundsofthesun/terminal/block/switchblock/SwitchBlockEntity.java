package io.github.soundsofthesun.terminal.block.switchblock;

import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.attachment.TransitAttachedData;
import io.github.soundsofthesun.terminal.block.TBlockEntities;
import io.github.soundsofthesun.terminal.block.properties.TProperties;
import io.github.soundsofthesun.terminal.util.TUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.RailBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class SwitchBlockEntity extends BlockEntity {

    public Box box;
    public BlockPos checkForward;
    public BlockPos checkLeft;
    public BlockPos checkRight;
    public BlockPos target;
    public Direction facing;
    public Direction left;
    public Direction right;

    public SwitchBlockEntity(BlockPos pos, BlockState state) {
        super(TBlockEntities.SWITCH_BLOCK_ENTITY, pos, state);
        this.facing = state.get(HorizontalFacingBlock.FACING);
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


        this.target = pos.offset(this.facing);
        this.checkForward = target.offset(this.facing, 2);
        this.checkLeft = target.offset(this.left, 2);
        this.checkRight = target.offset(this.right, 2);
        this.box = new Box(target).expand(1);
    }

    int i = 0;

    public static void tick(World world, BlockPos blockPos, BlockState blockState, SwitchBlockEntity blockEntity) {
        if (!(world instanceof ServerWorld serverWorld)) return;

        if (blockEntity.i <= 2) {
            blockEntity.i++;
            return;
        }

        if (serverWorld.getBlockState(blockPos).get(TProperties.ACTIVE_PROPERTY) == TProperties.ACTIVE_STATE.ACTIVE) {
            serverWorld.setBlockState(blockPos, blockState.with(TProperties.ACTIVE_PROPERTY, TProperties.ACTIVE_STATE.INACTIVE));
        }

        serverWorld.getEntitiesByClass(AbstractMinecartEntity.class, blockEntity.box, EntityPredicates.VALID_ENTITY).forEach(entity -> {
            Entity passenger = entity.getFirstPassenger();
            if (!(passenger instanceof PlayerEntity player)) return;
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
                if (bs.isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("rails")))) {
                    if (railShape == serverWorld.getBlockState(blockEntity.target).get(Properties.RAIL_SHAPE)) return;
                    serverWorld.setBlockState(blockPos, blockState.with(TProperties.ACTIVE_PROPERTY, TProperties.ACTIVE_STATE.ACTIVE));
                    serverWorld.setBlockState(blockEntity.target, bs.with(RailBlock.SHAPE, railShape));
                    world.playSound(null, blockPos, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.PLAYERS, 2F, 1F);
                }
            }
        });
        blockEntity.i = 0;
    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        super.onBlockReplaced(pos, oldState);//TODO
    }
}
