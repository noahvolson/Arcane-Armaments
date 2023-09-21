package net.noahvolson.rpgmod.entity.skill.warrior;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.entity.skill.ModAreaEffectCloud;
import net.noahvolson.rpgmod.entity.skill.Skill;
import net.noahvolson.rpgmod.particle.ModParticles;

import java.util.ArrayList;
import java.util.List;

public class WarCrySkill implements Skill {
    public final int DURATION = 40;

    public WarCrySkill(ServerPlayer player) {

    }

    @Override
    public void use(ServerPlayer player) {

        int i = Mth.floor(player.getX());
        int j = Mth.floor(player.getY() - (double)0.2F);
        int k = Mth.floor(player.getZ());
        BlockPos blockpos = new BlockPos(i, j, k);
        BlockState blockstate = player.level.getBlockState(blockpos);

        if (blockstate.isAir()) {
            player.addEffect(new MobEffectInstance(ModEffects.STOMPING.get(), Integer.MAX_VALUE, -1));
        } else {
            int rumbleRadius = 4;
            int rumbleDuration = 10;

            ModAreaEffectCloud rumbleCloud = new ModAreaEffectCloud(player.level, player.getX(), player.getY(), player.getZ());
            rumbleCloud.setParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(blockpos));
            rumbleCloud.setRadiusOnUse(0F);
            rumbleCloud.setRadiusPerTick((float) rumbleRadius / rumbleDuration);
            rumbleCloud.setDuration(rumbleDuration);
            rumbleCloud.setWaitTime(0);
            rumbleCloud.setOwner(player);
            rumbleCloud.addEffect(new MobEffectInstance(ModEffects.FEAR.get(), DURATION, 0, false, false, true));
            player.level.addFreshEntity(rumbleCloud);
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
