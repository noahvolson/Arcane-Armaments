package net.noahvolson.arcanearmaments.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class HolyShieldParticle extends TextureSheetParticle {
    private final SpriteSet sprites;
    double magnitude = .03;
    double randomXd = (2*Math.random() - 1) * magnitude;
    double randomYd = (2*Math.random() - 1) * magnitude;
    double randomZd = (2*Math.random() - 1) * magnitude;

    protected HolyShieldParticle(ClientLevel level, double xCord, double yCord, double zCord, SpriteSet spriteSet,
                            double xd, double yd, double zd) {
        super(level, xCord, yCord, zCord, xd, yd, zd);

        this.friction = 0.96F;
        this.xd = this.xd * (double)0.01F + xd;
        this.yd = this.yd * (double)0.01F + yd;
        this.zd = this.zd * (double)0.01F + zd;
        this.x += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.y += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.z += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
        this.sprites = spriteSet;
        this.setSpriteFromAge(spriteSet);   // Needed to not CTD
        this.quadSize *= 0.65F;             // Scale

        this.setColorRgb( new Color(254, 254, 254));

    }

    private void fadeOut() {
        this.alpha = (-(1/(float)lifetime) * age * 1);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        stepColor( new Color(254, 254, 254), new Color(254, 202, 94));
        if ((double) this.age / this.lifetime > 0.3) {
            this.xd = randomXd;
            this.yd = randomYd;
            this.zd = randomZd;
        }
        fadeOut();
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
            HolyShieldParticle HolyShieldParticle = new HolyShieldParticle(level, x, y, z, this.sprites, dx, dy, dz);
            HolyShieldParticle.scale(0.5F);
            return HolyShieldParticle;
        }
    }
}
