package io.github.soundsofthesun.terminal.network.c2s;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.attachment.NameAttachedData;
import io.github.soundsofthesun.terminal.attachment.StationAttachedData;
import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.attachment.TerminalAttachedData;
import io.github.soundsofthesun.terminal.block.stationblock.StationBlockEntity;
import io.github.soundsofthesun.terminal.block.terminalblock.TerminalBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import java.util.Objects;

public record StationRenameC2SPayload(String name, BlockPos pos) implements CustomPacketPayload {
    public static final ResourceLocation STATION_RENAME_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, "station_rename");
    public static final CustomPacketPayload.Type<StationRenameC2SPayload> ID = new CustomPacketPayload.Type<>(STATION_RENAME_PAYLOAD_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, StationRenameC2SPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, StationRenameC2SPayload::name,
            BlockPos.STREAM_CODEC, StationRenameC2SPayload::pos,
            StationRenameC2SPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static void initialize() {
        ServerPlayNetworking.registerGlobalReceiver(StationRenameC2SPayload.ID, ((payload, context) -> {
            ServerPlayer player = context.player();
            ServerLevel world = player.level();
            BlockEntity e = world.getBlockEntity(payload.pos());
            if (e instanceof StationBlockEntity sbe ) {
                TerminalAttachedData data = sbe.getAttached(TAttachmentTypes.TERMINAL_ATTACHED_DATA);
                BlockEntity t = world.getBlockEntity(data.terminal());
                if (t instanceof TerminalBlockEntity tbe ) {
                    StationAttachedData stations = tbe.getAttachedOrCreate(TAttachmentTypes.STATION_ATTACHMENT_TYPE);
                    if (!Objects.equals(stations.posMap().get(payload.pos().asLong()), payload.name())) {
                        StationAttachedData stations2 = stations.addPos(payload.pos().asLong(), payload.name());
                        tbe.setAttached(TAttachmentTypes.STATION_ATTACHMENT_TYPE, stations2);
                        sbe.setAttached(TAttachmentTypes.NAME_ATTACHED_DATA, new NameAttachedData(payload.name()));
                    }
                }
            }
        }));
    }
}
