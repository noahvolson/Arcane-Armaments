package net.noahvolson.arcanearmaments.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.noahvolson.arcanearmaments.entity.skill.SkillType;
import net.noahvolson.arcanearmaments.entity.skill.cleric.HealingAuraSkill;
import net.noahvolson.arcanearmaments.sound.ModSounds;

public class HealingAuraEffect extends MobEffect {

    public HealingAuraEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    // Applied as long as isDurationEffectTick is true
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof ServerPlayer player && pLivingEntity.level instanceof ServerLevel level) {
            int duration = player.getActiveEffectsMap().get(HealingAuraEffect.this).getDuration();
            if (duration < SkillType.HEALING_AURA.getDuration() && duration % 40 == 0) {
                HealingAuraSkill cloud = new HealingAuraSkill(player);
                level.addFreshEntity(cloud);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.HEALING_AURA.get(), SoundSource.HOSTILE, .9F, 1.2F / (level.random.nextFloat() * 0.2F + 0.9F));
                cloud.healHarmNearby(player);
            }
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}