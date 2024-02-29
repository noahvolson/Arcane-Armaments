package net.noahvolson.rpgmod.rpgclass;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.entity.skill.SkillType;
import net.noahvolson.rpgmod.entity.skill.Skill;
import net.noahvolson.rpgmod.entity.skill.SkillFactory;

public class RpgClass {

    private final int id;
    private final String label;
    private final Item classItem;
    private final int classColor;
    private final SkillType skill1;
    private final SkillType skill2;
    private final SkillType skill3;
    private final SkillType skill4;
    private final ResourceLocation background;

    public RpgClass(int id, String label, Item classItem, int classColor, SkillType skill1, SkillType skill2, SkillType skill3, SkillType skill4, ResourceLocation background) {
        this.id = id;
        this.label = label;
        this.classItem = classItem;
        this.classColor = classColor;
        this.skill1 = skill1;
        this.skill2 = skill2;
        this.skill3 = skill3;
        this.skill4 = skill4;
        this.background =  background;
    }

    public int getId() {
        return id;
    }
    public String getLabel() {
        return label;
    }
    public Item getClassItem() {
        return classItem;
    }
    public int getClassColor() {
        return classColor;
    }
    public SkillType getSkill1() {
        return skill1;
    }
    public SkillType getSkill2() {
        return skill2;
    }
    public SkillType getSkill3() {
        return skill3;
    }
    public SkillType getSkill4() {
        return skill4;
    }
    public ResourceLocation getBackground() {
        return background;
    }

    public void useSkill1(ServerPlayer player) {
        useSkill(player, skill1, ModEffects.COOLDOWN_1.get());
    }
    public void useSkill2(ServerPlayer player) {
        useSkill(player, skill2, ModEffects.COOLDOWN_2.get());
    }
    public void useSkill3(ServerPlayer player) {
        useSkill(player, skill3, ModEffects.COOLDOWN_3.get());
    }
    public void useSkill4(ServerPlayer player) {
        useSkill(player, skill4, ModEffects.COOLDOWN_4.get());
    }

    private void useSkill(ServerPlayer player, SkillType skillType, MobEffect cooldownEffect) {
        boolean DEBUG = false;
        boolean usedSkill = false;

        Skill skill = SkillFactory.getSkill(skillType, player);
        if (!player.hasEffect(cooldownEffect)) {
            player.addEffect(new MobEffectInstance(cooldownEffect, skillType.getCooldown(), 0, false, false, DEBUG));
            skill.use(player);
            usedSkill = true;
        } else if (skill.canUseTurnover(player)){
            int turnoverCooldown = skillType.getTurnoverCooldown();
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
