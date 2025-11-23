package io.github.soundsofthesun.terminal.network.c2s;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.attachment.TransitAttachedData;
import io.github.soundsofthesun.terminal.util.PathStations;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.VehicleEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public record StationTransitC2SPayload(BlockPos src, BlockPos dest) implements CustomPayload {

    public static final Identifier STATION_TRANSIT_PAYLOAD_ID = Identifier.of(Terminal.MOD_ID, "station_transit");
    public static final CustomPayload.Id<StationTransitC2SPayload> ID = new CustomPayload.Id<>(STATION_TRANSIT_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, StationTransitC2SPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, StationTransitC2SPayload::src,
            BlockPos.PACKET_CODEC, StationTransitC2SPayload::dest,
            StationTransitC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void initialize() {
        ServerPlayNetworking.registerGlobalReceiver(StationTransitC2SPayload.ID, ((payload, context) -> {
            ServerPlayerEntity player = context.player();
            ServerWorld world = player.getEntityWorld();
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof VehicleEntity && world.getBlockState(vehicle.getBlockPos()).isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("rails")))) {
                PathStations path = new PathStations(world);
                path.getRails(payload.src(), payload.dest());
                if (!path.validRails.isEmpty()) {
                    path.validRails.add(payload.dest());
                    player.setAttached(TAttachmentTypes.TRANSIT_ATTACHMENT_TYPE, new TransitAttachedData(new ArrayList<>(path.validRails)));
                    BlockPos current = vehicle.getBlockPos();
                    if (!(path.validRails.size() < 3)) {
                        BlockPos testPos = path.validRails.get(2);
                        int dx = testPos.getX()-current.getX();
                        int dz = testPos.getZ()-current.getZ();
                        vehicle.addVelocity(dx*5, 0, dz*5);
                        }
                    }
                }
        }));
    }
}
