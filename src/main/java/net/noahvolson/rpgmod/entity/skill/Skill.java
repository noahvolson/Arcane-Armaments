package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.server.level.ServerPlayer;

public interface Skill {
    public void use(ServerPlayer player);
    public void useTurnover(ServerPlayer player);
    public boolean canUseTurnover(ServerPlayer player);
    public int getCost();
    public int getCooldown();
    public int getTurnoverCooldown();
    public boolean isInvisibleCausing();

}
