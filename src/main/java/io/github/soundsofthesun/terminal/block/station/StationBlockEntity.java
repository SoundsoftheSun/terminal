package io.github.soundsofthesun.terminal.block.station;

import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.attachment.TransitAttachedData;
import io.github.soundsofthesun.terminal.block.TBlockEntities;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class StationBlockEntity extends BlockEntity {
    public StationBlockEntity(BlockPos pos, BlockState state) {
        super(TBlockEntities.STATION_BLOCK_ENTITY, pos, state);
        this.box = new AABB(pos).inflate(1);
        this.i = 0;
    }

    public AABB box;
    int i = 0;

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, StationBlockEntity blockEntity) {
        if (!(world instanceof ServerLevel serverWorld)) return;

        if (blockEntity.i <= 5) {
            blockEntity.i++;
            return;
        }

        serverWorld.getEntitiesOfClass(AbstractMinecart.class, blockEntity.box, EntitySelector.ENTITY_STILL_ALIVE).forEach(entity -> {
            Entity passenger = entity.getFirstPassenger();
            if (!(passenger instanceof Player player)) return;
            TransitAttachedData data = player.getAttachedOrCreate(TAttachmentTypes.TRANSIT_ATTACHMENT_TYPE);
            List<BlockPos> posList = data.get();
            if (posList.isEmpty()) return;

            if (blockPos.asLong() == posList.getLast().asLong()) {
                Vec3 velocity = entity.getDeltaMovement();
                entity.push(-velocity.x*.8, 0, -velocity.z*.8);
            }
        });

        blockEntity.i = 0;

    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState oldState) {
        super.preRemoveSideEffects(pos, oldState);//TODO
    }

}
