package net.noahvolson.rpgmod.entity.skill.warrior;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.Vec3;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.entity.skill.Skill;

import java.util.ArrayList;
import java.util.List;

public class WarCrySkill implements Skill {
    private final int DURATION = 40;

    public WarCrySkill(ServerPlayer player) {

    }

    @Override
    public void use(ServerPlayer player) {
        List<LivingEntity> list = player.level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, player, player.getBoundingBox().inflate(5, 5, 5));
        for (LivingEntity target : list) {
            target.addEffect(new MobEffectInstance(ModEffects.FEAR.get(), DURATION, 0, false, false, true));
        }
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
