package net.noahvolson.rpgmod.effect;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.noahvolson.rpgmod.entity.skill.cleric.HealingAuraSkill;
import net.noahvolson.rpgmod.particle.ModParticles;
import net.noahvolson.rpgmod.sound.ModSounds;

import java.util.ArrayList;
import java.util.List;

public class HealingAuraEffect extends MobEffect {

    public HealingAuraEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    // Applied as long as isDurationEffectTick is true
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof ServerPlayer player && pLivingEntity.level instanceof ServerLevel level) {
            int duration = player.getActiveEffectsMap().get(HealingAuraEffect.this).getDuration();
            if (duration < HealingAuraSkill.EFFECT_DURATION && duration % 40 == 0) {
                HealingAuraSkill cloud = new HealingAuraSkill(player);
                level.addFreshEntity(cloud);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.SMOKE_BOMB.get(), SoundSource.HOSTILE, .9F, 1.2F / (level.random.nextFloat() * 0.2F + 0.9F));
            }
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}