package io.github.soundsofthesun.terminal.client;

import io.github.soundsofthesun.terminal.client.data.TLangProviderEn;
import io.github.soundsofthesun.terminal.client.data.TModelProvider;
import io.github.soundsofthesun.terminal.client.data.TRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class TerminalDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(TModelProvider::new);
        pack.addProvider(TRecipeProvider::new);

        pack.addProvider(TLangProviderEn::new);
    }
}
