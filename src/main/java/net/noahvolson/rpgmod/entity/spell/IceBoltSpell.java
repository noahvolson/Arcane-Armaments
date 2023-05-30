package net.noahvolson.rpgmod.entity.spell;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

public class IceBoltSpell extends AbstractProjectileSpell {

    public IceBoltSpell(EntityType<AbstractProjectileSpell> entityType, Level world) {
        super(entityType, world);
    }

    public IceBoltSpell(EntityType<AbstractProjectileSpell> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, SoundEvents.ILLUSIONER_CAST_SPELL, SoundEvents.GLASS_BREAK, SoundEvents.GLASS_BREAK);
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        if (!this.level.isClientSide && ray.getEntity() instanceof LivingEntity livingentity) {
            livingentity.addEffect(new MobEffectInstance(ModEffects.FREEZE.get(), 100, -1));
        }
    }

    @Override
    protected void doEffectsBlock(@NotNull BlockHitResult ray) {
        EntityType.POTION.create(level);
    };

    @Override
    protected void tickDespawn() {
        if (this.inGroundTime > 120) {
            this.discard();
        }
    }

    @Override
    protected void makeParticle() {
        if (!this.inGround) {
            for(int j = 0; j < 5; ++j) {

                double magnitude = .02;
                double xD = (2*Math.random() - 1) * magnitude;
                double yD = (2*Math.random() - 1) * magnitude;
                double zD = (2*Math.random() - 1) * magnitude;

                this.level.addParticle(ModParticles.ICEBOLT_PARTICLES.get(), this.getX(), this.getY(), this.getZ(), xD, yD, zD);
                this.level.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        } else {

        }
    }

}