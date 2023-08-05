package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.server.level.ServerPlayer;

public interface Skill {
    public void use(ServerPlayer player);
    public int getCost();
    public int getRecharge();
    public boolean causesStealth();
}
