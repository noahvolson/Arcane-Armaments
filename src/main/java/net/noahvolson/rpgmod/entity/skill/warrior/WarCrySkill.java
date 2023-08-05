package net.noahvolson.rpgmod.entity.skill.warrior;

import net.minecraft.server.level.ServerPlayer;
import net.noahvolson.rpgmod.entity.skill.Skill;

public class WarCrySkill implements Skill {
    @Override
    public void use(ServerPlayer player) {

    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public int getRecharge() {
        return 0;
    }

    @Override
    public boolean causesStealth() {
        return false;
    }
}
