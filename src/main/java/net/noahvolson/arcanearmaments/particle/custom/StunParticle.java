package net.noahvolson.arcanearmaments.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class StunParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected StunParticle(ClientLevel level, double xCord, double yCord, double zCord, SpriteSet spriteSet,
                           double xd, double yd, double zd) {
        super(level, xCord, yCord, zCord, 0.0D, 0.0D, 0.0D);

        float f = this.random.nextFloat() * 0.6F + 0.4F;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;

        this.quadSize *= 0.8F;             // Scale
        this.lifetime = 10;                // How long shown in ticks

        this.sprites = spriteSet;
        this.setSpriteFromAge(spriteSet);   // Needed to not CTD
        this.setColorRgb(new Color(254, 254, 254));

    }
    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
        }
    }

    private void setColorRgb(Color color) {
        this.rCol = 255f - color.getRed();
        this.gCol = 255f - color.getGreen();
        this.bCol = 255f - color.getBlue();
    }

    public int getLightColor(float p_106821_) {
        return 255;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
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
            return new StunParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
