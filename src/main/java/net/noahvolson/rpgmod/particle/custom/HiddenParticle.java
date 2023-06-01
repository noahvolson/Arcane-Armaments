package net.noahvolson.rpgmod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class HiddenParticle extends TextureSheetParticle {

    protected HiddenParticle(ClientLevel level, double xCord, double yCord, double zCord, SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCord, yCord, zCord, xd, yd, zd);

        this.friction = 0.8F;   // Air friction?

        // Velocity
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;

        this.quadSize *= 0.85F;             // Scale
        this.lifetime = 1;                 // How long shown in ticks
        this.alpha = 0;

        this.setSpriteFromAge(spriteSet);   // Needed to not CTD

    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new HiddenParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
