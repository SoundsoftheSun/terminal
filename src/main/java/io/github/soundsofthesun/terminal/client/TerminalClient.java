package io.github.soundsofthesun.terminal.client;

import io.github.soundsofthesun.terminal.block.TBlocks;
import io.github.soundsofthesun.terminal.item.TItems;
import io.github.soundsofthesun.terminal.particle.TParticles;
import net.fabricmc.api.ClientModInitializer;

public class TerminalClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        TParticles.initializeClient();
        TBlocks.initializeClient();
        TItems.initializeClient();
    }
}
