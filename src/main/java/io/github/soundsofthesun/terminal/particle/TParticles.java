package io.github.soundsofthesun.terminal.particle;

import io.github.soundsofthesun.terminal.Terminal;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TParticles {
    public static final SimpleParticleType DEBUG_PARTICLE = FabricParticleTypes.simple();

    public static void initialize() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Terminal.MOD_ID, "debug_particle"), DEBUG_PARTICLE);
    }

    public static void initializeClient() {
        ParticleFactoryRegistry.getInstance().register(DEBUG_PARTICLE, DebugParticle.Factory::new);

    }
}
