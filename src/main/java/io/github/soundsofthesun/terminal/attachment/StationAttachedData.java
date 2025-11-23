package io.github.soundsofthesun.terminal.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public record StationAttachedData(LinkedHashMap<Long, String> posMap) {

    private record Entry(Long pos, String value) {
        public static final Codec<Entry> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.LONG.fieldOf("pos").forGetter(Entry::pos),
                        Codec.STRING.fieldOf("value").forGetter(Entry::value)
                ).apply(instance, Entry::new)
        );
    }

    public static final Codec<StationAttachedData> CODEC = Entry.CODEC
            .listOf()
            .xmap(
                    entries -> {
                        LinkedHashMap<Long, String> map = new LinkedHashMap<>();
                        for (Entry e : entries) map.put(e.pos(), e.value());
                        return new StationAttachedData(new LinkedHashMap<>(map));
                    },
                    data -> data.posMap().entrySet().stream()
                            .map(e -> new Entry(e.getKey(), e.getValue()))
                            .collect(Collectors.toList())
            );

    public static final PacketCodec<ByteBuf, StationAttachedData> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public static final StationAttachedData DEFAULT = new StationAttachedData(new LinkedHashMap<>());

    public StationAttachedData {
        if (posMap == null) throw new NullPointerException("posMap cannot be null");
    }

    public StationAttachedData addPos(Long pos, String value) {
        LinkedHashMap<Long, String> newMap = new LinkedHashMap<>(posMap);
        newMap.put(pos, value);
        return new StationAttachedData(new LinkedHashMap<>(newMap));
    }

    public StationAttachedData removePos(Long pos) {
        if (!posMap.containsKey(pos)) return this;
        LinkedHashMap<Long, String> newMap = new LinkedHashMap<>(posMap);
        newMap.remove(pos);
        return new StationAttachedData(new LinkedHashMap<>(newMap));
    }

    public StationAttachedData clear() {
        return DEFAULT;
    }

    public String get(Long pos) {
        return posMap.get(pos);
    }
}