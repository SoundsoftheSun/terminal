package io.github.soundsofthesun.terminal.particle;

import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class DebugParticle extends BillboardParticle {
    public DebugParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Sprite sprite) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, sprite);

        this.velocityMultiplier = 0.0f;

        this.maxAge = 7;

        this.scale = 0.25F;

        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;

    }

    @Override
    protected RenderType getRenderType() {
        return RenderType.PARTICLE_ATLAS_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new DebugParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider.getSprite(1, 10));
        }
    }
}
