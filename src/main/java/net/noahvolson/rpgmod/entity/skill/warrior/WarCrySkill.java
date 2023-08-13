package net.noahvolson.rpgmod.entity.skill.warrior;

import net.minecraft.server.level.ServerPlayer;
import net.noahvolson.rpgmod.entity.skill.Skill;

public class WarCrySkill implements Skill {

    public WarCrySkill(ServerPlayer player) {
    }

    @Override
    public void use(ServerPlayer player) {

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
    public int getCooldown() {
        return 0;
    }

    @Override
    public int getTurnoverCooldown() {
        return 0;
    }

    @Override
    public boolean isInvisibleCausing() {
        return false;
    }
}
