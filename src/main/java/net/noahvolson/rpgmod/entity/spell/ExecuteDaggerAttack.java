package net.noahvolson.rpgmod.entity.spell;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.particle.ModParticles;
import net.noahvolson.rpgmod.sound.ModSounds;
import org.jetbrains.annotations.NotNull;

public class ExecuteDaggerAttack extends AbstractMeleeAttack{
    private final int DURATION = 100;

    public ExecuteDaggerAttack(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public ExecuteDaggerAttack(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, ModSounds.EXECUTE_DAGGER.get());
        this.setBaseDamage(1);
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        Entity entity = ray.getEntity();
        if (!this.level.isClientSide && entity instanceof LivingEntity livingentity) {

            if (livingentity.getHealth() / livingentity.getMaxHealth() <= 0.33) {
                livingentity.hurt(new DamageSource("execute"), livingentity.getHealth() * 100);
            }

            double yShift = 1;
            AreaEffectCloud bloodCloud = new AreaEffectCloud(livingentity.level, livingentity.getX(), livingentity.blockPosition().getY() + yShift, livingentity.getZ());
            bloodCloud.setParticle(ModParticles.BLOOD_PARTICLES.get());
            bloodCloud.setRadius(.25F);
            bloodCloud.setDuration(5);
            bloodCloud.setWaitTime(0);
            this.level.addFreshEntity(bloodCloud);
        }
    };
}
