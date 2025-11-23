package io.github.soundsofthesun.terminal.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record NameAttachedData(String name) {
    public static Codec<NameAttachedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(NameAttachedData::name)
    ).apply(instance, NameAttachedData::new));

    public static StreamCodec<ByteBuf, NameAttachedData> PACKET_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public static NameAttachedData DEFAULT = new NameAttachedData("Unnamed");

    public NameAttachedData clear() {
        return DEFAULT;
    }
}