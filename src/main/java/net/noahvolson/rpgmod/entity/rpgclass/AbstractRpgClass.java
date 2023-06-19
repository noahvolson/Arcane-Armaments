package net.noahvolson.rpgmod.entity.rpgclass;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.noahvolson.rpgmod.entity.skill.Skill;

public abstract class AbstractRpgClass {
    
    ServerPlayer player;
    SkillTypes skillSlot1;
    SkillTypes skillSlot2;
    SkillTypes skillSlot3;
    SkillTypes skillSlot4;
    SkillFactory skillFactory;

    public AbstractRpgClass(ServerPlayer player, SkillTypes s_1, SkillTypes s_2, SkillTypes s_3, SkillTypes s_4) {
        this.player = player;
        this.skillSlot1 = s_1;
        this.skillSlot2 = s_2;
        this.skillSlot3 = s_3;
        this.skillSlot4 = s_4;
        this.skillFactory = new SkillFactory();
    }

    public void useSlot1() {
        useSkill(this.skillSlot1);
    }
    public void useSlot2() {
        useSkill(this.skillSlot2);
    }
    public void useSlot3() {
        useSkill(this.skillSlot3);
    }
    public void useSlot4() {
        useSkill(this.skillSlot4);
    }

    private void useSkill(SkillTypes slotToMake) {
        Skill skill = skillFactory.getSkill(slotToMake, player);
        skill.use(player);
        if (!player.isCreative()) {
            int remainingFood = player.getFoodData().getFoodLevel() - skill.getCost();
            player.getFoodData().setFoodLevel(Math.max(remainingFood, 0));
            if (remainingFood < 0) {
                player.hurt(new DamageSource("overcast"), Math.abs(remainingFood));
            }
        }
        player.swing(InteractionHand.OFF_HAND, true);
    }
}
