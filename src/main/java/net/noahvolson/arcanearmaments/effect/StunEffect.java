package net.noahvolson.arcanearmaments.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.noahvolson.arcanearmaments.particle.ModParticles;

public class StunEffect extends MobEffect {

    public StunEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    // Applied as long as isDurationEffectTick is true
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level().isClientSide()) {
            pLivingEntity.getActiveEffects().forEach(mobEffectInstance -> {
                if (mobEffectInstance.getEffect() instanceof StunEffect && mobEffectInstance.getDuration() % 10 == 0) {
                    Vec3 eyePos = pLivingEntity.getEyePosition();
                    AreaEffectCloud fearCloud = new AreaEffectCloud(pLivingEntity.level(), pLivingEntity.getX(), eyePos.y, pLivingEntity.getZ());
                    fearCloud.setParticle(ModParticles.STUN_PARTICLES.get());
                    fearCloud.setRadius(0.6F);
                    fearCloud.setDuration(5);
                    fearCloud.setWaitTime(0);
                    pLivingEntity.level().addFreshEntity(fearCloud);
                }
            });
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
