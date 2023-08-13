package net.noahvolson.rpgmod.entity.rpgclass;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.entity.skill.Skill;
import net.noahvolson.rpgmod.entity.skill.SkillFactory;

public abstract class AbstractRpgClass {
    
    ServerPlayer player;
    SkillTypes skillSlot1;
    SkillTypes skillSlot2;
    SkillTypes skillSlot3;
    SkillTypes skillSlot4;
    SkillFactory skillFactory;

    final boolean DEBUG = true;

    public AbstractRpgClass(ServerPlayer player, SkillTypes s_1, SkillTypes s_2, SkillTypes s_3, SkillTypes s_4) {
        this.player = player;
        this.skillSlot1 = s_1;
        this.skillSlot2 = s_2;
        this.skillSlot3 = s_3;
        this.skillSlot4 = s_4;
        this.skillFactory = new SkillFactory();
    }

    public void useSlot1() {
        useSkill(skillFactory.getSkill(skillSlot1, player), ModEffects.COOLDOWN_1.get());
    }
    public void useSlot2() {
        useSkill(skillFactory.getSkill(skillSlot2, player), ModEffects.COOLDOWN_2.get());
    }
    public void useSlot3() {
        useSkill(skillFactory.getSkill(skillSlot3, player), ModEffects.COOLDOWN_3.get());
    }
    public void useSlot4() {
        useSkill(skillFactory.getSkill(skillSlot4, player), ModEffects.COOLDOWN_4.get());
    }

    private void useSkill(Skill skill, MobEffect cooldownEffect) {
        boolean usedSkill = false;

        if (!player.hasEffect(cooldownEffect)) {
            player.addEffect(new MobEffectInstance(cooldownEffect, skill.getCooldown(), 0, false, false, DEBUG));

            skill.use(player);
            if (!player.isCreative()) {
                int remainingFood = player.getFoodData().getFoodLevel() - skill.getCost();
                player.getFoodData().setFoodLevel(Math.max(remainingFood, 0));
                if (remainingFood < 0) {
                    player.hurt(new DamageSource("overcast"), Math.abs(remainingFood));
                }
            }
            usedSkill = true;
        } else if (skill.canUseTurnover(player)){
            int turnoverCooldown = skill.getTurnoverCooldown();
            if (turnoverCooldown > 0 && !player.hasEffect(ModEffects.COOLDOWN_6.get())) {
                skill.useTurnover(player);
                player.addEffect(new MobEffectInstance(ModEffects.COOLDOWN_6.get(), turnoverCooldown, 0, false, false, DEBUG));
                usedSkill = true;
            }
        }

        if (usedSkill) {
            player.swing(InteractionHand.OFF_HAND, true);
            if (!skill.isInvisibleCausing() && player.hasEffect(MobEffects.INVISIBILITY)) {
                player.removeEffect(MobEffects.INVISIBILITY);
            }
        }
    }
}
