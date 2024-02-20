package net.noahvolson.rpgmod.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.phys.Vec3;
import net.noahvolson.rpgmod.entity.skill.SkillType;
import net.noahvolson.rpgmod.particle.ModParticles;

public class RuptureEffect extends MobEffect {

    public RuptureEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    // Applied as long as isDurationEffectTick is true
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide() && !(pLivingEntity instanceof AbstractSkeleton)) {
            int duration = pLivingEntity.getActiveEffectsMap().get(RuptureEffect.this).getDuration();
            if (duration < SkillType.RUPTURE.getDuration() && duration % 10 == 0) {
                Vec3 delta = pLivingEntity.getDeltaMovement();
                double damage = Math.floor((Math.abs(delta.x) + Math.abs(delta.y) + Math.abs(delta.z)) * 4);
                if (damage > 0) {
                    pLivingEntity.hurt(new DamageSource("rupture"), (float) damage);
                    double yShift = 0.5;
                    AreaEffectCloud bloodCloud = new AreaEffectCloud(pLivingEntity.level, pLivingEntity.getX(), pLivingEntity.blockPosition().getY() + yShift, pLivingEntity.getZ());
                    bloodCloud.setParticle(ModParticles.BLOOD_PARTICLES.get());
                    bloodCloud.setRadius(.25F);
                    bloodCloud.setDuration(5);
                    bloodCloud.setWaitTime(0);
                    pLivingEntity.level.addFreshEntity(bloodCloud);
                }
            }
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
