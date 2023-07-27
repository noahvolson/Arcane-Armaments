package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.particle.ModParticles;

public class SmokeBombCloud extends AreaEffectCloud implements Skill{
    private final int DURATION = 100;

    public SmokeBombCloud(EntityType<? extends AreaEffectCloud> entityType, Level world) {
        super(entityType, world);
    }

    public SmokeBombCloud(ServerPlayer player) {
        super(player.level, player.getX(), player.getY(), player.getZ());
        this.setParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE);
        this.setRadius(2F);
        this.setDuration(10);
        this.setWaitTime(0);
    }

    @Override
    public void use(ServerPlayer player) {
        player.level.addFreshEntity(this);
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, DURATION, -1));
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public int getRecharge() {
        return 0;
    }
}
