package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.server.level.ServerPlayer;
import net.noahvolson.rpgmod.entity.ModEntityTypes;
import net.noahvolson.rpgmod.entity.rpgclass.SkillTypes;

public class SkillFactory {

    public Skill getSkill(SkillTypes selection, ServerPlayer player) {
        return switch (selection) {
            // mage
            case FIREBALL   -> new FireballSpell(ModEntityTypes.FIREBALL.get(), player, player.level);
            case BLINK      -> new BlinkSpell(ModEntityTypes.BLINK.get(), player, player.level);
            case BLIZZARD   -> new BlizzardSpell(ModEntityTypes.BLIZZARD.get(), player, player.level);
            case THUNDER    -> new ThunderSpell(ModEntityTypes.THUNDER.get(), player, player.level);

            // rogue
            case SMOKE_BOMB     -> new SmokeBombCloud(player);
            case VENOM_DAGGER   -> new VenomDaggerAttack(ModEntityTypes.VENOM_DAGGER.get(), player, player.level);
            case RUPTURE_DAGGER -> new RuptureDaggerAttack(ModEntityTypes.RUPTURE_DAGGER.get(), player, player.level);
            case EXECUTE_DAGGER -> new ExecuteDaggerAttack(ModEntityTypes.EXECUTE_DAGGER.get(), player, player.level);
        };
    }
}
