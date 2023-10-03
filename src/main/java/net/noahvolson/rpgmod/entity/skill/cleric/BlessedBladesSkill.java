package net.noahvolson.rpgmod.entity.skill.cleric;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.entity.skill.Skill;
import net.noahvolson.rpgmod.particle.ModParticles;
import net.noahvolson.rpgmod.sound.ModSounds;

public class BlessedBladesSkill extends AreaEffectCloud implements Skill {
    private final int DURATION = 160;

    public BlessedBladesSkill(EntityType<? extends AreaEffectCloud> entityType, Level world) {
        super(entityType, world);
    }

    public BlessedBladesSkill(ServerPlayer player) {
        super(player.level, player.getX(), player.getY() + 0.5, player.getZ());
        this.setParticle(ModParticles.BLESSED_BLADE_PARTICLES.get());
        this.setRadius(2F);
        this.setDuration(10);
        this.setWaitTime(0);
    }

    @Override
    public void use(ServerPlayer player) {
        player.level.addFreshEntity(this);
        player.addEffect(new MobEffectInstance(ModEffects.BLESSED_BLADE.get(), DURATION, -1));
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

