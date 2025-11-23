package io.github.soundsofthesun.terminal.network.s2c;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.attachment.StationAttachedData;
import io.github.soundsofthesun.terminal.client.screen.StationScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record StationInteractS2CPayload(StationAttachedData data, BlockPos source) implements CustomPacketPayload {
    public static final ResourceLocation STATION_INTERACT_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, "station_interact");
    public static final CustomPacketPayload.Type<StationInteractS2CPayload> ID = new CustomPacketPayload.Type<>(STATION_INTERACT_PAYLOAD_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, StationInteractS2CPayload> CODEC = StreamCodec.composite(
            StationAttachedData.PACKET_CODEC,
            StationInteractS2CPayload::data,
            BlockPos.STREAM_CODEC,
            StationInteractS2CPayload::source,
            StationInteractS2CPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
