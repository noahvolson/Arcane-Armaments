package net.noahvolson.rpgmod.entity.skill.cleric;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.entity.skill.Skill;
import net.noahvolson.rpgmod.particle.ModParticles;
import net.noahvolson.rpgmod.sound.ModSounds;

public class HealingAuraSkill extends AreaEffectCloud implements Skill {

    public static final int CLOUD_RADIUS = 1;
    public static final int CLOUD_DURATION = 10;
    public static final int EFFECT_DURATION = 200;


    public HealingAuraSkill(EntityType<? extends AreaEffectCloud> entityType, Level world) {
        super(entityType, world);
    }

    public HealingAuraSkill(ServerPlayer player) {
        super(player.level, player.getX(), player.getY() + 0.65, player.getZ());
        this.setParticle(ModParticles.HEAL_PARTICLES.get());
        this.setRadiusOnUse(0F);
        this.setRadiusPerTick((float) CLOUD_RADIUS / CLOUD_DURATION);
        this.setDuration(CLOUD_DURATION);
        this.addEffect(new MobEffectInstance(MobEffects.HEAL, 20, 1, false, false, true));
        this.setWaitTime(0);
    }

    @Override
    public void use(ServerPlayer player) {
        player.level.addFreshEntity(this);
        player.addEffect(new MobEffectInstance(ModEffects.HEALING_AURA.get(), EFFECT_DURATION, 0, false, false, true));
        this.level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.SMOKE_BOMB.get(), SoundSource.HOSTILE, .9F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    public void useTurnover(ServerPlayer player) {

    }

    @Override
    public boolean canUseTurnover(ServerPlayer player) {
        return false;
    }


    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public boolean isInvisibleCausing() {
        return true;
    }

}
