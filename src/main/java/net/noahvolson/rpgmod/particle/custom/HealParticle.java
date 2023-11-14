package net.noahvolson.rpgmod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class HealParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected HealParticle(ClientLevel level, double xCord, double yCord, double zCord, SpriteSet spriteSet,
                                 double xd, double yd, double zd) {
        super(level, xCord, yCord, zCord, xd, yd, zd);

        this.friction = 0.96F;
        this.xd = 0;
        this.yd = (this.yd * (double)0.01F + yd);
        this.zd = 0;
        this.x += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.y += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.z += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.lifetime = 15;
        this.sprites = spriteSet;
        this.setSpriteFromAge(spriteSet);   // Needed to not CTD
        this.quadSize *= 1.5;

        if (Math.random() > 0.6) {
            this.alpha = 0.4F;
        }

        this.setColorRgb( new Color(254, 254, 254));

    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
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
            HealParticle HealParticle = new HealParticle(level, x, y, z, this.sprites, dx, dy, dz);
            HealParticle.scale(0.5F);
            return HealParticle;
        }
    }
}
