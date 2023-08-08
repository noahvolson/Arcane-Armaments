package net.noahvolson.rpgmod.effect;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.noahvolson.rpgmod.particle.ModParticles;
import net.noahvolson.rpgmod.sound.ModSounds;

public class ZappedEffect extends MobEffect {
    public ZappedEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    // Applied as long as isDurationEffectTick is true
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {
            pLivingEntity.getActiveEffects().forEach(mobEffectInstance -> {
                if (mobEffectInstance.getEffect() instanceof ZappedEffect && mobEffectInstance.getDuration() <= 1) {
                    LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, pLivingEntity.level);
                    bolt.setPos(pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ());
                    pLivingEntity.level.addFreshEntity(bolt);
                }
            });

            pLivingEntity.getActiveEffects().forEach(mobEffectInstance -> {
                if (mobEffectInstance.getEffect() instanceof ZappedEffect && mobEffectInstance.getDuration() % 20 == 0) {
                    pLivingEntity.level.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(),
                            ModSounds.THUNDER_PULSE.get(), SoundSource.HOSTILE, .5f, 1f);

                    AreaEffectCloud zapCloud = new AreaEffectCloud(pLivingEntity.level, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ());
                    zapCloud.setParticle(ModParticles.ZAPPED_PARTICLES.get());
                    zapCloud.setRadius(1.5F);
                    zapCloud.setDuration(5);
                    zapCloud.setWaitTime(0);
                    pLivingEntity.level.addFreshEntity(zapCloud);
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