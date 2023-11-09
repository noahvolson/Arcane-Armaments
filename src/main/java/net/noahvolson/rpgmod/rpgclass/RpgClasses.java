package net.noahvolson.rpgmod.rpgclass;

import net.minecraft.world.item.Items;

import static net.noahvolson.rpgmod.entity.skill.SkillType.*;

public class RpgClasses {
    public static final RpgClass MAGE = new RpgClass("MAGE", Items.STICK, FIREBALL, BLINK, BLIZZARD, THUNDER);
    public static final RpgClass ROGUE = new RpgClass("ROGUE", Items.IRON_SWORD, VENOM_DAGGER, SMOKE_BOMB, RUPTURE_DAGGER, EXECUTE_DAGGER);
    public static final RpgClass WARRIOR = new RpgClass("WARRIOR", Items.IRON_AXE, BERSERK, STOMP, GRAPPLE, MOLTEN_SHELL);
    public static final RpgClass CLERIC = new RpgClass("CLERIC", Items.NETHER_STAR, BLESSED_BLADES, HOLY_SHIELD, SMITING_RAY, HEALING_AURA);
}
