package io.github.soundsofthesun.terminal.network.c2s;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.attachment.TransitAttachedData;
import io.github.soundsofthesun.terminal.util.PathStations;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import java.util.ArrayList;

public record StationTransitC2SPayload(BlockPos src, BlockPos dest) implements CustomPacketPayload {

    public static final ResourceLocation STATION_TRANSIT_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, "station_transit");
    public static final CustomPacketPayload.Type<StationTransitC2SPayload> ID = new CustomPacketPayload.Type<>(STATION_TRANSIT_PAYLOAD_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, StationTransitC2SPayload> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, StationTransitC2SPayload::src,
            BlockPos.STREAM_CODEC, StationTransitC2SPayload::dest,
            StationTransitC2SPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static void initialize() {
        ServerPlayNetworking.registerGlobalReceiver(StationTransitC2SPayload.ID, ((payload, context) -> {
            ServerPlayer player = context.player();
            ServerLevel world = player.level();
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof VehicleEntity && world.getBlockState(vehicle.blockPosition()).is(TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("rails")))) {
                PathStations path = new PathStations(world);
                path.getRails(payload.src(), payload.dest());
                if (!path.validRails.isEmpty()) {
                    path.validRails.add(payload.dest());
                    player.setAttached(TAttachmentTypes.TRANSIT_ATTACHMENT_TYPE, new TransitAttachedData(new ArrayList<>(path.validRails)));
                    BlockPos current = vehicle.blockPosition();
                    if (!(path.validRails.size() < 4)) {
                        BlockPos testPos = path.validRails.get(3);
                        int dx = testPos.getX()-current.getX();
                        int dz = testPos.getZ()-current.getZ();
                        vehicle.push(dx*5, 0, dz*5);
                        }
                    }
                }
        }));
    }
}
