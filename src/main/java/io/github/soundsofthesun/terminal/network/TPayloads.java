package io.github.soundsofthesun.terminal.network;

import io.github.soundsofthesun.terminal.network.c2s.StationRenameC2SPayload;
import io.github.soundsofthesun.terminal.network.c2s.StationTransitC2SPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class TPayloads {
    public static void initialize() {
        PayloadTypeRegistry.playC2S().register(StationTransitC2SPayload.ID, StationTransitC2SPayload.CODEC);
        StationTransitC2SPayload.initialize();

        PayloadTypeRegistry.playC2S().register(StationRenameC2SPayload.ID, StationRenameC2SPayload.CODEC);
        StationRenameC2SPayload.initialize();
    }
}
