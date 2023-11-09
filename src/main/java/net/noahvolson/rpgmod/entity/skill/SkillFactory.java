package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.server.level.ServerPlayer;
import net.noahvolson.rpgmod.entity.ModEntityTypes;
import net.noahvolson.rpgmod.entity.skill.cleric.BlessedBladesSkill;
import net.noahvolson.rpgmod.entity.skill.cleric.HolyShieldSkill;
import net.noahvolson.rpgmod.entity.skill.cleric.SmitingRaySkill;
import net.noahvolson.rpgmod.entity.skill.mage.BlinkSpell;
import net.noahvolson.rpgmod.entity.skill.mage.BlizzardSpell;
import net.noahvolson.rpgmod.entity.skill.mage.FireballSpell;
import net.noahvolson.rpgmod.entity.skill.mage.ThunderSpell;
import net.noahvolson.rpgmod.entity.skill.rogue.ExecuteDaggerAttack;
import net.noahvolson.rpgmod.entity.skill.rogue.RuptureDaggerAttack;
import net.noahvolson.rpgmod.entity.skill.rogue.SmokeBombSkill;
import net.noahvolson.rpgmod.entity.skill.rogue.VenomDaggerAttack;
import net.noahvolson.rpgmod.entity.skill.warrior.BerserkSkill;
import net.noahvolson.rpgmod.entity.skill.warrior.MoltenShellSkill;
import net.noahvolson.rpgmod.entity.skill.warrior.GrappleAttack;
import net.noahvolson.rpgmod.entity.skill.warrior.StompSkill;

public class SkillFactory {

    public static Skill getSkill(SkillType selection, ServerPlayer player) {
        return switch (selection) {

            // Mage
            case FIREBALL   -> new FireballSpell(ModEntityTypes.FIREBALL.get(), player, player.level);
            case BLINK      -> new BlinkSpell(ModEntityTypes.BLINK.get(), player, player.level);
            case BLIZZARD   -> new BlizzardSpell(ModEntityTypes.BLIZZARD.get(), player, player.level);
            case THUNDER    -> new ThunderSpell(ModEntityTypes.THUNDER.get(), player, player.level);

            // Rogue
            case SMOKE_BOMB     -> new SmokeBombSkill(player);
            case VENOM_DAGGER   -> new VenomDaggerAttack(ModEntityTypes.VENOM_DAGGER.get(), player, player.level);
            case RUPTURE_DAGGER -> new RuptureDaggerAttack(ModEntityTypes.RUPTURE_DAGGER.get(), player, player.level);
            case EXECUTE_DAGGER -> new ExecuteDaggerAttack(ModEntityTypes.EXECUTE_DAGGER.get(), player, player.level);

            // Warrior
            case BERSERK -> new BerserkSkill(player);
            case STOMP -> new StompSkill(player);
            case GRAPPLE -> new GrappleAttack(ModEntityTypes.GRAPPLE.get(), player, player.level);
            case MOLTEN_SHELL -> new MoltenShellSkill(player);

            // Cleric
            case BLESSED_BLADES -> new BlessedBladesSkill(player);
            case HOLY_SHIELD -> new HolyShieldSkill(player);
            case SMITING_RAY -> new SmitingRaySkill(ModEntityTypes.SMITING_RAY.get(), player, player.level);
            case HEALING_AURA -> new BlessedBladesSkill(player);

        };
    }
}
