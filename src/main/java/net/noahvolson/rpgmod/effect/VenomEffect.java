package net.noahvolson.rpgmod.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.phys.Vec3;
import net.noahvolson.rpgmod.entity.skill.SkillType;
import net.noahvolson.rpgmod.particle.ModParticles;

public class VenomEffect extends MobEffect {


    public VenomEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    // Applied as long as isDurationEffectTick is true
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        int duration = pLivingEntity.getActiveEffectsMap().get(VenomEffect.this).getDuration();
        if (duration < SkillType.ENVENOM.getDuration() && duration % 30 == 0) {
            if (pLivingEntity.level instanceof ServerLevel serverLevel && pLivingEntity.getMobType() != MobType.UNDEAD) {
                    pLivingEntity.hurt(new DamageSource("venom"), 2);
                    Vec3 eyePos = pLivingEntity.getEyePosition();
                    for (int j = 0; j < 3; ++j) {

                        double magnitude = .1;
                        double xD = (2 * Math.random() - 1) * magnitude;
                        double yD = (2 * Math.random() - 1) * magnitude;
                        double zD = (2 * Math.random() - 1) * magnitude;

                        serverLevel.sendParticles(ModParticles.VENOM_PARTICLES.get(), eyePos.x, eyePos.y + 0.5, eyePos.z, 1, 0, xD, yD, zD);
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
