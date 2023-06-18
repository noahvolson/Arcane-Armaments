package net.noahvolson.rpgmod.entity.spell;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.particle.ModParticles;
import net.noahvolson.rpgmod.sound.ModSounds;
import org.jetbrains.annotations.NotNull;

public class RuptureDaggerAttack extends AbstractMeleeAttack{
    private final int DURATION = 100;

    public RuptureDaggerAttack(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public RuptureDaggerAttack(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, ModSounds.RUPTURE_DAGGER.get());
        this.setBaseDamage(1);
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        Entity entity = ray.getEntity();
        if (entity.level instanceof ServerLevel serverLevel && entity instanceof LivingEntity livingentity) {
            livingentity.addEffect(new MobEffectInstance(ModEffects.RUPTURED.get(), DURATION, -1));

            double yShift = 1;
            AreaEffectCloud bloodCloud = new AreaEffectCloud(livingentity.level, livingentity.getX(), livingentity.blockPosition().getY() + yShift, livingentity.getZ());
            bloodCloud.setParticle(ModParticles.BLOOD_PARTICLES.get());
            bloodCloud.setRadius(.25F);
            bloodCloud.setDuration(5);
            bloodCloud.setWaitTime(0);
            this.level.addFreshEntity(bloodCloud);

            Vec3 eyePos = livingentity.getEyePosition();
            serverLevel.sendParticles(ModParticles.DAGGER_PARTICLES.get(), eyePos.x(), eyePos.y(), eyePos.z(), 1, 0D, 0D,0D, 0D);
        }
    };
}