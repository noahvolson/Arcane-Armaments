package net.noahvolson.rpgmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.noahvolson.rpgmod.particle.ModParticles;

import java.util.Objects;

public class BerserkEffect extends MobEffect {
    public BerserkEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    // Applied as long as isDurationEffectTick is true
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        if (!pLivingEntity.level.isClientSide()) {

            // TODO Fix how this interacts with swiftness
            this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);

            AreaEffectCloud cloud = new AreaEffectCloud(pLivingEntity.level, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ());
            cloud.setParticle(ModParticles.BERSERK_PARTICLES.get());
            cloud.setRadius(.5F);
            cloud.setDuration(5);
            cloud.setWaitTime(0);
            pLivingEntity.level.addFreshEntity(cloud);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
