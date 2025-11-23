package io.github.soundsofthesun.terminal.block.station;

import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.attachment.TransitAttachedData;
import io.github.soundsofthesun.terminal.block.TBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class StationBlockEntity extends BlockEntity {
    public StationBlockEntity(BlockPos pos, BlockState state) {
        super(TBlockEntities.STATION_BLOCK_ENTITY, pos, state);
        this.box = new Box(pos).expand(1);
        this.i = 0;
    }

    public Box box;
    int i = 0;

    public static void tick(World world, BlockPos blockPos, BlockState blockState, StationBlockEntity blockEntity) {
        if (!(world instanceof ServerWorld serverWorld)) return;

        if (blockEntity.i <= 5) {
            blockEntity.i++;
            return;
        }

        serverWorld.getEntitiesByClass(AbstractMinecartEntity.class, blockEntity.box, EntityPredicates.VALID_ENTITY).forEach(entity -> {
            Entity passenger = entity.getFirstPassenger();
            if (!(passenger instanceof PlayerEntity player)) return;
            TransitAttachedData data = player.getAttachedOrCreate(TAttachmentTypes.TRANSIT_ATTACHMENT_TYPE);
            List<BlockPos> posList = data.get();
            if (posList.isEmpty()) return;

            if (blockPos.asLong() == posList.getLast().asLong()) {
                Vec3d velocity = entity.getVelocity();
                entity.addVelocity(-velocity.x*.8, 0, -velocity.z*.8);
            }
        });

        blockEntity.i = 0;

    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        super.onBlockReplaced(pos, oldState);//TODO
    }

}
