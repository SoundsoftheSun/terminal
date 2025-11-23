package io.github.soundsofthesun.terminal.network;

import io.github.soundsofthesun.terminal.client.screen.StationScreen;
import io.github.soundsofthesun.terminal.network.s2c.StationInteractS2CPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class TClientPayloads {
    public static void initialize() {
        ClientPlayNetworking.registerGlobalReceiver(StationInteractS2CPayload.ID, ((payload, context) -> {
            context.client().execute(() ->
                    context.client().setScreen(new StationScreen(payload.data().posMap(), payload.source()))
            );
        }));
    }
}
