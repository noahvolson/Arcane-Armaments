package net.noahvolson.arcanearmaments.entity.skill.cleric;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.noahvolson.arcanearmaments.effect.ModEffects;
import net.noahvolson.arcanearmaments.entity.skill.Skill;
import net.noahvolson.arcanearmaments.entity.skill.SkillType;
import net.noahvolson.arcanearmaments.particle.ModParticles;

public class BlessedBladesSkill extends AreaEffectCloud implements Skill {

    public BlessedBladesSkill(ServerPlayer player) {
        super(player.level, player.getX(), player.getY() + 0.5, player.getZ());
        this.setParticle(ModParticles.BLESSED_BLADE_PARTICLES.get());
        this.setRadius(3F);
        this.setDuration(10);
        this.setWaitTime(0);
    }

    @Override
    public void use(ServerPlayer player) {
        player.level.addFreshEntity(this);
        player.addEffect(new MobEffectInstance(ModEffects.BLESSED_BLADE.get(), SkillType.BLESSED_BLADES.getDuration(), -1));
        this.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ILLUSIONER_PREPARE_MIRROR, SoundSource.HOSTILE, .9F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
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
        return false;
    }

}

