package io.github.soundsofthesun.terminal.network.s2c;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.attachment.StationAttachedData;
import io.github.soundsofthesun.terminal.client.screen.StationScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record StationInteractS2CPayload(StationAttachedData data, BlockPos source) implements CustomPayload {
    public static final Identifier STATION_INTERACT_PAYLOAD_ID = Identifier.of(Terminal.MOD_ID, "station_interact");
    public static final CustomPayload.Id<StationInteractS2CPayload> ID = new CustomPayload.Id<>(STATION_INTERACT_PAYLOAD_ID);

    public static final PacketCodec<RegistryByteBuf, StationInteractS2CPayload> CODEC = PacketCodec.tuple(
            StationAttachedData.PACKET_CODEC,
            StationInteractS2CPayload::data,
            BlockPos.PACKET_CODEC,
            StationInteractS2CPayload::source,
            StationInteractS2CPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
