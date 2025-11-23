package io.github.soundsofthesun.terminal.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record TransitAttachedData(List<BlockPos> validRails) {

    public static Codec<TransitAttachedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.listOf().fieldOf("validRails").forGetter(TransitAttachedData::validRails)
    ).apply(instance, TransitAttachedData::new));

    public static StreamCodec<ByteBuf, TransitAttachedData> PACKET_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public static TransitAttachedData DEFAULT = new TransitAttachedData(new ArrayList<>());

    public TransitAttachedData set(LinkedHashSet<BlockPos> path) {
        return new TransitAttachedData(new ArrayList<>(path));
    }

    public TransitAttachedData clear() {
        return DEFAULT;
    }

    public List<BlockPos> get() {
        return new ArrayList<>(validRails);
    }
}
