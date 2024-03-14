package net.noahvolson.rpgmod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ZappedParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected ZappedParticle(ClientLevel level, double xCord, double yCord, double zCord, SpriteSet spriteSet,
                              double xd, double yd, double zd) {
        super(level, xCord, yCord, zCord, xd, yd, zd);

        this.friction = 0.75F;   // Air friction?

        // Velocity
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.quadSize *= 1F;             // Scale
        this.lifetime = 20;                 // How long shown in ticks

        this.sprites = spriteSet;
        this.setSpriteFromAge(spriteSet);   // Needed to not CTD

        this.setColorRgb(new Color(248, 245, 245));

    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        this.stepColor(new Color(226, 215, 241), new Color(166, 95, 246));
        this.setParticleSpeed(this.xd * 1.4, this.yd * 1.4, this.zd * 1.4);
        this.quadSize *= 0.97F;
        fadeOut();
    }

    private void fadeOut() {
        this.alpha = (-(1/(float)lifetime) * age * 1);
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
            return new ZappedParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
