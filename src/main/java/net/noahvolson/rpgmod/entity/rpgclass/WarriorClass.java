package net.noahvolson.rpgmod.entity.rpgclass;

import net.minecraft.server.level.ServerPlayer;

public class WarriorClass extends AbstractRpgClass{
    public WarriorClass(ServerPlayer player) {
        super(player, SkillTypes.BERSERK, SkillTypes.WARCRY, SkillTypes.MEATHOOK, SkillTypes.DEFIANCE);
    }
}
