package io.github.soundsofthesun.terminal.network.c2s;

import io.github.soundsofthesun.terminal.Terminal;
import io.github.soundsofthesun.terminal.attachment.NameAttachedData;
import io.github.soundsofthesun.terminal.attachment.StationAttachedData;
import io.github.soundsofthesun.terminal.attachment.TAttachmentTypes;
import io.github.soundsofthesun.terminal.attachment.TerminalAttachedData;
import io.github.soundsofthesun.terminal.block.station.StationBlockEntity;
import io.github.soundsofthesun.terminal.block.terminalblock.TerminalBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public record StationRenameC2SPayload(String name, BlockPos pos) implements CustomPayload {
    public static final Identifier STATION_RENAME_PAYLOAD_ID = Identifier.of(Terminal.MOD_ID, "station_rename");
    public static final CustomPayload.Id<StationRenameC2SPayload> ID = new CustomPayload.Id<>(STATION_RENAME_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, StationRenameC2SPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, StationRenameC2SPayload::name,
            BlockPos.PACKET_CODEC, StationRenameC2SPayload::pos,
            StationRenameC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void initialize() {
        ServerPlayNetworking.registerGlobalReceiver(StationRenameC2SPayload.ID, ((payload, context) -> {
            ServerPlayerEntity player = context.player();
            ServerWorld world = player.getEntityWorld();
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
