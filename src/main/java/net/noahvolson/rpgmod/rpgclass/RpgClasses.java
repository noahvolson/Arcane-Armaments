package net.noahvolson.rpgmod.rpgclass;

import net.minecraft.world.item.Items;

import static net.noahvolson.rpgmod.entity.skill.SkillType.*;

public class RpgClasses {
    public static final RpgClass MAGE = new RpgClass("MAGE", Items.STICK, BLINK, FIREBALL, BLIZZARD, THUNDER);
    public static final RpgClass ROGUE = new RpgClass("ROGUE", Items.IRON_SWORD, SMOKE_BOMB, VENOM_DAGGER, RUPTURE_DAGGER, EXECUTE_DAGGER);
    public static final RpgClass WARRIOR = new RpgClass("WARRIOR", Items.IRON_AXE, BERSERK, WARCRY, MEATHOOK, DEFIANCE);
}
