package io.github.soundsofthesun.terminal.particle;

import io.github.soundsofthesun.terminal.Terminal;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class TParticles {
    public static final SimpleParticleType DEBUG_PARTICLE = FabricParticleTypes.simple();

    public static void initialize() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(Terminal.MOD_ID, "debug_particle"), DEBUG_PARTICLE);
    }

    public static void initializeClient() {
        ParticleFactoryRegistry.getInstance().register(DEBUG_PARTICLE, DebugParticle.Factory::new);

    }
}
