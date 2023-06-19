package net.noahvolson.rpgmod.entity.rpgclass;

import net.minecraft.server.level.ServerPlayer;

public class MageClass extends AbstractRpgClass{
    public MageClass(ServerPlayer player) {
        super(player, SkillTypes.FIREBALL, SkillTypes.BLINK, SkillTypes.BLIZZARD, SkillTypes.THUNDER);
    }
}
