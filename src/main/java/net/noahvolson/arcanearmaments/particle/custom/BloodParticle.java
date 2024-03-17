package net.noahvolson.arcanearmaments.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BloodParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected BloodParticle(ClientLevel level, double xCord, double yCord, double zCord, SpriteSet spriteSet,
                               double xd, double yd, double zd) {
        super(level, xCord, yCord, zCord, xd, yd, zd);

        this.gravity = 0.75F;
        this.friction = 0.2F;

        // Velocity
        this.xd = xd * 0.4;
        this.yd = yd;
        this.zd = zd * 0.4;

        this.quadSize *= 0.60F;             // Scale
        this.lifetime = 15;                 // How long shown in ticks

        this.sprites = spriteSet;
        this.setSpriteFromAge(spriteSet);   // Needed to not CTD

        this.setColorRgb(new Color(182, 178, 178));

    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        //this.stepColor(new Color(246, 214, 65), new Color(255, 94, 0));
        this.setParticleSpeed(this.xd, this.yd * 1.8, this.zd);
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
            return new BloodParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
