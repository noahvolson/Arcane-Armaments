package net.noahvolson.rpgmod.entity.spell;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.noahvolson.rpgmod.particle.ModParticles;
import net.noahvolson.rpgmod.sound.ModSounds;
import org.jetbrains.annotations.NotNull;

public class PoisonDaggerAttack extends AbstractMeleeAttack{
    private final int DURATION = 60;

    public PoisonDaggerAttack(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
        this.setBaseDamage(1);
    }

    public PoisonDaggerAttack(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, ModSounds.POISON_DAGGER.get());
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        Entity entity = ray.getEntity();
        if (entity.level instanceof ServerLevel serverLevel && entity instanceof LivingEntity livingentity) {
            livingentity.addEffect(new MobEffectInstance(MobEffects.POISON, DURATION, 1));

            double yShift = 1;
            AreaEffectCloud poisonCloud = new AreaEffectCloud(livingentity.level, livingentity.getX(), livingentity.blockPosition().getY() + yShift, livingentity.getZ());
            poisonCloud.setParticle(ModParticles.POISON_PARTICLES.get());
            poisonCloud.setRadius(.45F);
            poisonCloud.setDuration(5);
            poisonCloud.setWaitTime(0);
            this.level.addFreshEntity(poisonCloud);

            Vec3 eyePos = livingentity.getEyePosition();
            serverLevel.sendParticles(ModParticles.DAGGER_PARTICLES.get(), eyePos.x(), eyePos.y(), eyePos.z(), 1, 0D, 0D,0D, 0D);
        }
    };
}