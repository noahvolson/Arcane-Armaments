package net.noahvolson.rpgmod.entity.spell;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.fml.common.Mod;
import net.noahvolson.rpgmod.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

public class ThunderSpell extends AbstractProjectileSpell {
    public ThunderSpell(EntityType<AbstractProjectileSpell> entityType, Level world) {
        super(entityType, world);
    }

    public ThunderSpell(EntityType<AbstractProjectileSpell> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, SoundEvents.GHAST_SHOOT);
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
    }

    @Override
    protected void doEffectsBlock(@NotNull BlockHitResult ray) {
    };

    // To blow up after 3 seconds
    @Override
    protected void tickDespawn() {
        if (this.inGroundTime > 80){
            //this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.BlockInteraction.NONE);
            LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level);
            bolt.setPos(this.getX(), this.getY(), this.getZ());
            this.level.addFreshEntity(bolt);
            this.discard();
        }
    }

    @Override
    protected void makeParticle() {
        if (!this.inGround) {
            for(int j = 0; j < 5; ++j) {

                double magnitude = .03;
                double xD = (2*Math.random() - 1) * magnitude;
                double yD = (2*Math.random() - 1) * magnitude;
                double zD = (2*Math.random() - 1) * magnitude;

                this.level.addParticle(ModParticles.THUNDER_PARTICLES.get(), this.getX(), this.getY(), this.getZ(), xD, yD, zD);
            }
        }
        else if (this.inGroundTime % 20 == 0 || this.inGroundTime % 20 == 19 || this.inGroundTime % 20 == 18) {
            for(int j = 0; j < 3; ++j) {

                double magnitude = .04;
                double yD = Math.random() * magnitude;
                this.level.addParticle(ModParticles.THUNDER_PARTICLES.get(), this.getX(), this.getY(), this.getZ(), 0, yD, 0);
                this.level.addParticle(ModParticles.THUNDER_PARTICLES.get(), this.getX() + 1, this.getY(), this.getZ(), 0, yD, 0);
                this.level.addParticle(ModParticles.THUNDER_PARTICLES.get(), this.getX() - 1, this.getY(), this.getZ(), 0, yD, 0);
                this.level.addParticle(ModParticles.THUNDER_PARTICLES.get(), this.getX(), this.getY(), this.getZ() + 1, 0, yD, 0);
                this.level.addParticle(ModParticles.THUNDER_PARTICLES.get(), this.getX(), this.getY(), this.getZ() - 1, 0, yD, 0);
            }
        }
    }

}