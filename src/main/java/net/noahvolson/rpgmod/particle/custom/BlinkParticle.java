package net.noahvolson.rpgmod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BlinkParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected BlinkParticle(ClientLevel level, double xCord, double yCord, double zCord, SpriteSet spriteSet,
                               double xd, double yd, double zd) {
        super(level, xCord, yCord, zCord, xd, yd, zd);

        this.friction = 0.8F;   // Air friction?

        // Velocity
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.quadSize *= 5F;             // Scale
        this.lifetime = 15;                 // How long shown in ticks

        this.sprites = spriteSet;
        this.setSpriteFromAge(spriteSet);   // Needed to not CTD

        this.setColorRgb(new Color(133, 198, 86));

    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        this.stepColor(new Color(113, 172, 73), new Color(73, 130, 147));
        this.setParticleSpeed(this.xd * 1.4, this.yd * 1.4, this.zd * 1.4);
        fadeOut();
    }


    private void fadeOut() {
        this.alpha = (-(1/(float)lifetime) * age * 1);
    }

    private void stepColor(Color start, Color end) {
        int stepR = (end.getRed() - start.getRed()) / lifetime;
        int stepG = (end.getGreen() - start.getGreen()) / lifetime;
        int stepB = (end.getBlue() - start.getBlue()) / lifetime;

        this.setColorRgb(
                new Color(
                        start.getRed() + (stepR * age),
                        start.getGreen() + (stepG * age),
                        start.getBlue() + (stepB * age)
                )
        );
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
            return new BlinkParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
