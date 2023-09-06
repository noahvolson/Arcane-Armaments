package net.noahvolson.rpgmod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.noahvolson.rpgmod.effect.ShellEffect;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ShellParticle extends TextureSheetParticle {
    private final SpriteSet sprites;
    double magnitude = .03;
    double randomXd = (2*Math.random() - 1) * magnitude;
    double randomYd = (2*Math.random() - 1) * magnitude;
    double randomZd = (2*Math.random() - 1) * magnitude;

    protected ShellParticle(ClientLevel level, double xCord, double yCord, double zCord, SpriteSet spriteSet,
                              double xd, double yd, double zd) {
        super(level, xCord, yCord, zCord, xd, yd, zd);

        System.out.println("xd, yd, zd: " + xd + "," + yd + "," + zd);

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

        this.setColorRgb(new Color(254, 254, 254));

    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        if ((double) this.age / this.lifetime > 0.3) {
            this.xd = randomXd;
            this.yd = randomYd;
            this.zd = randomZd;
        }
    }

    private void setColorRgb(Color color) {
        this.rCol = 255f - color.getRed();
        this.gCol = 255f - color.getGreen();
        this.bCol = 255f - color.getBlue();
    }
    
    public float getQuadSize(float p_106824_) {
        float f = ((float)this.age + p_106824_) / (float)this.lifetime;
        return this.quadSize * (1.0F - f * f * 0.5F);
    }

    public int getLightColor(float p_106821_) {
        float f = ((float)this.age + p_106821_) / (float)this.lifetime;
        f = Mth.clamp(f, 0.0F, 1.0F);
        int i = super.getLightColor(p_106821_);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
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
            ShellParticle shellParticle = new ShellParticle(level, x, y, z, this.sprites, dx, dy, dz);
            shellParticle.scale(0.5F);
            return shellParticle;
        }
    }
}
