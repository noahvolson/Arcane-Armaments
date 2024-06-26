package net.noahvolson.arcanearmaments.entity.skill.mage;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.noahvolson.arcanearmaments.damage.ModDamageSources;
import net.noahvolson.arcanearmaments.effect.ModEffects;
import net.noahvolson.arcanearmaments.entity.skill.AbstractProjectileAbility;
import net.noahvolson.arcanearmaments.entity.skill.SkillType;
import net.noahvolson.arcanearmaments.particle.ModParticles;
import net.noahvolson.arcanearmaments.sound.ModSounds;
import org.jetbrains.annotations.NotNull;

public class ThunderSpell extends AbstractProjectileAbility {

    public ThunderSpell(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public ThunderSpell(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, ModSounds.THUNDER_CAST.get(), ModSounds.THUNDER_IMPACT.get(), ModSounds.THUNDER_IMPACT.get());
        this.setDamage(new ModDamageSources(this.level().registryAccess()).zap(), SkillType.THUNDER.getDamage());
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        if (!this.level().isClientSide && ray.getEntity() instanceof LivingEntity livingentity) {
            livingentity.addEffect(new MobEffectInstance(ModEffects.ZAPPED.get(), SkillType.THUNDER.getDuration(), -1));
        }
    }

    // To blow up after 3 seconds
    @Override
    protected void tickDespawn() {
        if (this.inGroundTime > SkillType.THUNDER.getDuration()){
            //this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.BlockInteraction.NONE);
            LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
            bolt.setPos(this.getX(), this.getY(), this.getZ());
            this.level().addFreshEntity(bolt);
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.makeTrailParticle();
        } else if (this.inGroundTime % 20 == 0 && this.inGroundTime > 0) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.THUNDER_PULSE.get(), SoundSource.HOSTILE, .5f, 1f);

            AreaEffectCloud zapCloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            zapCloud.setParticle(ModParticles.ZAPPED_PARTICLES.get());
            zapCloud.setRadius(1.5F);
            zapCloud.setDuration(5);
            zapCloud.setWaitTime(0);
            this.level().addFreshEntity(zapCloud);
        }
    }

    @Override
    protected void makeTrailParticle() {
        if (!this.inGround) {
            for(int j = 0; j < 5; ++j) {

                double magnitude = .03;
                double xD = (2*Math.random() - 1) * magnitude;
                double yD = (2*Math.random() - 1) * magnitude;
                double zD = (2*Math.random() - 1) * magnitude;

                this.level().addParticle(ModParticles.THUNDER_PARTICLES.get(), this.getX(), this.getY(), this.getZ(), xD, yD, zD);
            }
        }
        else if (this.inGroundTime % 20 == 0 || this.inGroundTime % 20 == 19 || this.inGroundTime % 20 == 18) {

            double magnitudeD = 0;//.02;
            double yD = Math.random() * .01;

            int numParticles = 20;
            double shiftMagnitude = 1D / numParticles;

            // Vertical line top to start
            for(double i = 0; i < numParticles; ++i) {
                double shift = i * shiftMagnitude;
                double x = this.getX();
                double y = this.getY();
                double z = this.getZ() - 1 + shift;
                this.level().addParticle(ModParticles.RUNE_PARTICLES.get(), x, y, z, 0, yD, 0);
            }

            // Vertical line down from start
            for(double i = 0; i < numParticles; ++i) {
                double shift = i * shiftMagnitude;
                double x = this.getX();
                double y = this.getY();
                double z = this.getZ() + shift;
                this.level().addParticle(ModParticles.RUNE_PARTICLES.get(), x, y, z, 0, yD, 0);
            }

            // Left half angled line from start
            for(double i = 0; i < (double) numParticles / 2; ++i) {
                double shift = i * shiftMagnitude;
                double x = this.getX() - shift;
                double y = this.getY();
                double z = this.getZ() - shift;
                this.level().addParticle(ModParticles.RUNE_PARTICLES.get(), x, y, z, 0, yD, 0);
            }

            // Right half angled line from start
            for(double i = 0; i < (double) numParticles / 2; ++i) {
                double shift = i * shiftMagnitude;
                double x = this.getX() + shift;
                double y = this.getY();
                double z = this.getZ() - shift;
                this.level().addParticle(ModParticles.RUNE_PARTICLES.get(), x, y, z, 0, yD, 0);
            }

            // Left half angled line from top
            for(double i = 0; i < (double) numParticles / 2; ++i) {
                double shift = i * shiftMagnitude;
                double x = this.getX() - shift;
                double y = this.getY();
                double z = this.getZ() + shift - 1;
                this.level().addParticle(ModParticles.RUNE_PARTICLES.get(), x, y, z, 0, yD, 0);
            }

            // Right half angled line from top
            for(double i = 0; i < (double) numParticles / 2; ++i) {
                double shift = i * shiftMagnitude;
                double x = this.getX() + shift;
                double y = this.getY();
                double z = this.getZ() + shift - 1;
                this.level().addParticle(ModParticles.RUNE_PARTICLES.get(), x, y, z, 0, yD, 0);
            }

            // Left half angled line from start
            for(double i = 0; i < (double) numParticles / 2; ++i) {
                double shift = i * shiftMagnitude;
                double x = this.getX() - shift;
                double y = this.getY();
                double z = this.getZ() + shift;
                this.level().addParticle(ModParticles.RUNE_PARTICLES.get(), x, y, z, 0, yD, 0);
            }

            // Right half angled line from start
            for(double i = 0; i < (double) numParticles / 2; ++i) {
                double shift = i * shiftMagnitude;
                double x = this.getX() + shift;
                double y = this.getY();
                double z = this.getZ() + shift;
                this.level().addParticle(ModParticles.RUNE_PARTICLES.get(), x, y, z, 0, yD, 0);
            }
            
        }
    }

}