package net.noahvolson.arcanearmaments.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class FreezeParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected FreezeParticle(ClientLevel level, double xCord, double yCord, double zCord, SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCord, yCord, zCord, xd, yd, zd);
        this.gravity = 0.225F;
        this.friction = 1.0F;
        this.sprites = spriteSet;

        this.xd = xd + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
        this.yd = yd + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
        this.zd = zd + (Math.random() * 2.0D - 1.0D) * (double)0.05F;

        this.quadSize = 0.1F * (this.random.nextFloat() * this.random.nextFloat() * 1.0F + 1.0F);
        this.lifetime = (int)(16.0D / ((double)this.random.nextFloat() * 0.8D + 0.2D)) + 2;

        this.setSpriteFromAge(spriteSet);   // Needed to not CTD
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        this.xd *= (double)0.95F;
        this.yd *= (double)0.9F;
        this.zd *= (double)0.95F;

        this.stepColor(new Color(231, 242, 245), new Color(76, 225, 227));
    }

    private void stepColor(Color start, Color end) {
        float stepR = (float) (end.getRed() - start.getRed()) / lifetime;
        float stepG = (float) (end.getGreen() - start.getGreen()) / lifetime;
        float stepB = (float) (end.getBlue() - start.getBlue()) / lifetime;

        int r = Math.round(start.getRed() + (stepR * age));
        int g = Math.round(start.getGreen() + (stepG * age));
        int b = Math.round(start.getBlue() + (stepB * age));

        r = r >= 255 ? 254 : (r <= 0 ? 1 : r);
        g = g >= 255 ? 254 : (g <= 0 ? 1 : g);
        b = b >= 255 ? 254 : (b <= 0 ? 1 : b);

        this.setColorRgb(new Color(r,g,b));
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
            return new FreezeParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
