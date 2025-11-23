package io.github.soundsofthesun.terminal.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.BlockPos;

public record TerminalAttachedData(BlockPos terminal) {
    public static Codec<TerminalAttachedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.fieldOf("terminal").forGetter(TerminalAttachedData::terminal)
    ).apply(instance, TerminalAttachedData::new));

    public static PacketCodec<ByteBuf, TerminalAttachedData> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public static TerminalAttachedData DEFAULT = new TerminalAttachedData(new BlockPos(0, -64, 0));

    public TerminalAttachedData clear() {
        return DEFAULT;
    }
}