package net.noahvolson.rpgmod.entity.rpgclass;

import net.minecraft.server.level.ServerPlayer;

public class RogueClass extends AbstractRpgClass{
    public RogueClass(ServerPlayer player) {
        super(player, SkillTypes.POISON_DAGGER, SkillTypes.RUPTURE_DAGGER, SkillTypes.EXECUTE_DAGGER, SkillTypes.THUNDER);
    }
}
