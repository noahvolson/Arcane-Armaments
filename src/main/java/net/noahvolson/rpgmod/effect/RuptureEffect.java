package net.noahvolson.rpgmod.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.noahvolson.rpgmod.particle.ModParticles;

public class RuptureEffect extends MobEffect {

    private final float MODIFIER = 4;

    private Double lastX;
    private Double lastY;
    private Double lastZ;

    private int tickCounter = 0;

    public RuptureEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    // Applied as long as isDurationEffectTick is true
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        double x = pLivingEntity.getX();
        double y = pLivingEntity.getY();
        double z = pLivingEntity.getZ();

        if (!pLivingEntity.level.isClientSide() && !(pLivingEntity instanceof AbstractSkeleton)) {
            if (tickCounter > 0 && tickCounter % 10 == 0) {
                if (lastX != null && lastY != null && lastZ != null) {
                    float distance = (float) Math.sqrt(Math.pow(x - lastX, 2) + Math.pow(y - lastY, 2) + Math.pow(z - lastZ, 2));
                    if (distance > 0) {
                        float damage = Math.min(distance, 4.0f);
                        pLivingEntity.hurt(new DamageSource("rupture"), damage * MODIFIER);
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
            this.tickCounter++;
            this.lastX = x;
            this.lastY = y;
            this.lastZ = z;
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
