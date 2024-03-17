package net.noahvolson.arcanearmaments.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class VenomParticle extends TextureSheetParticle {
    private final SpriteSet sprites;
    private final double xStart;
    private final double yStart;
    private final double zStart;

    protected VenomParticle(ClientLevel level, double xCord, double yCord, double zCord, SpriteSet spriteSet,
                            double xd, double yd, double zd) {
        super(level, xCord, yCord, zCord, xd, yd, zd);

        // From enchantment table particle
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.xStart = xCord;
        this.yStart = yCord;
        this.zStart = zCord;
        this.xo = xCord + xd;
        this.yo = yCord + yd;
        this.zo = zCord + zd;
        this.x = this.xo;
        this.y = this.yo;
        this.z = this.zo;
        float f = this.random.nextFloat() * 0.6F + 0.4F;
        this.rCol = 0.9F * f;
        this.gCol = 0.9F * f;
        this.bCol = f;
        this.hasPhysics = false;
        this.lifetime = (int)(Math.random() * 10.0D) + 10;

        this.sprites = spriteSet;
        this.setSpriteFromAge(spriteSet);   // Needed to not CTD
        this.setColorRgb(new Color(227, 246, 166));
    }

    public void tick() {
        this.setSpriteFromAge(this.sprites);
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            float f = (float)this.age / (float)this.lifetime;
            f = 1.0F - f;
            float f1 = 1.0F - f;
            f1 *= f1;
            f1 *= f1;
            this.x = this.xStart + this.xd * (double)f;
            this.y = this.yStart + this.yd * (double)f + (f1 * 0.5);
            this.z = this.zStart + this.zd * (double)f;
        }
        fadeOut();
    }
    private void fadeOut() {
        this.alpha = (-(1/(float)lifetime) * age * 1);
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
            return new VenomParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
