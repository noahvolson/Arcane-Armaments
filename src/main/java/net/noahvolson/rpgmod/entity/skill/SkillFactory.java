package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.server.level.ServerPlayer;
import net.noahvolson.rpgmod.entity.ModEntityTypes;
import net.noahvolson.rpgmod.entity.rpgclass.SkillTypes;
import net.noahvolson.rpgmod.entity.skill.mage.BlinkSpell;
import net.noahvolson.rpgmod.entity.skill.mage.BlizzardSpell;
import net.noahvolson.rpgmod.entity.skill.mage.FireballSpell;
import net.noahvolson.rpgmod.entity.skill.mage.ThunderSpell;
import net.noahvolson.rpgmod.entity.skill.rogue.ExecuteDaggerAttack;
import net.noahvolson.rpgmod.entity.skill.rogue.RuptureDaggerAttack;
import net.noahvolson.rpgmod.entity.skill.rogue.SmokeBombSkill;
import net.noahvolson.rpgmod.entity.skill.rogue.VenomDaggerAttack;
import net.noahvolson.rpgmod.entity.skill.warrior.BerserkSkill;
import net.noahvolson.rpgmod.entity.skill.warrior.DefianceSkill;
import net.noahvolson.rpgmod.entity.skill.warrior.MeatHookAttack;
import net.noahvolson.rpgmod.entity.skill.warrior.WarCrySkill;

public class SkillFactory {

    public Skill getSkill(SkillTypes selection, ServerPlayer player) {
        return switch (selection) {
            // mage
            case FIREBALL   -> new FireballSpell(ModEntityTypes.FIREBALL.get(), player, player.level);
            case BLINK      -> new BlinkSpell(ModEntityTypes.BLINK.get(), player, player.level);
            case BLIZZARD   -> new BlizzardSpell(ModEntityTypes.BLIZZARD.get(), player, player.level);
            case THUNDER    -> new ThunderSpell(ModEntityTypes.THUNDER.get(), player, player.level);

            // rogue
            case SMOKE_BOMB     -> new SmokeBombSkill(player);
            case VENOM_DAGGER   -> new VenomDaggerAttack(ModEntityTypes.VENOM_DAGGER.get(), player, player.level);
            case RUPTURE_DAGGER -> new RuptureDaggerAttack(ModEntityTypes.RUPTURE_DAGGER.get(), player, player.level);
            case EXECUTE_DAGGER -> new ExecuteDaggerAttack(ModEntityTypes.EXECUTE_DAGGER.get(), player, player.level);

            // warrior
            case BERSERK -> new BerserkSkill(player);
            case WARCRY -> new WarCrySkill(player);
            case MEATHOOK -> new MeatHookAttack(player);
            case DEFIANCE -> new DefianceSkill(player);

        };
    }
}
