package net.noahvolson.rpgmod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class FireBoltParticles extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected FireBoltParticles(ClientLevel level, double xCord, double yCord, double zCord, SpriteSet spriteSet,
                                double xd, double yd, double zd) {
        super(level, xCord, yCord, zCord, xd, yd, zd);

        this.friction = 0.8F;   // Air friction?

        // Velocity
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.quadSize *= 0.85F;             // Scale
        this.lifetime = 20;                 // How long shown in ticks

        this.sprites = spriteSet;
        this.setSpriteFromAge(spriteSet);   // Needed to not CTD

        this.rCol = 255f - 246f;
        this.gCol = 255f - 214;
        this.bCol = 255f - 65f;

    }

    int tickCounter = 0;
    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        //fadeOut();
    }

    private void fadeOut() {
        this.alpha = (-(1/(float)lifetime) * age * 1);
    }

    private void stepColor() {
        // From #F6D641     >   rgb(246, 214, 65)
        // To   #FF5E00     >   rgb(255, 94, 0)

        // Difference       >   (9,-120,-65)
    }

    @Override
    public ParticleRenderType getRenderType() {
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
            return new FireBoltParticles(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
