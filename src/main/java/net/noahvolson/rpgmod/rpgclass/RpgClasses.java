package net.noahvolson.rpgmod.rpgclass;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import static net.noahvolson.rpgmod.entity.skill.SkillType.*;

public class RpgClasses {
    public static final RpgClass MAGE = new RpgClass(0, "MAGE", Items.STICK, FIREBALL, BLINK, BLIZZARD, THUNDER);
    public static final RpgClass ROGUE = new RpgClass(1, "ROGUE", Items.IRON_SWORD, VENOM_DAGGER, SMOKE_BOMB, RUPTURE_DAGGER, EXECUTE_DAGGER);
    public static final RpgClass WARRIOR = new RpgClass(2, "WARRIOR", Items.IRON_AXE, BERSERK, STOMP, GRAPPLE, MOLTEN_SHELL);
    public static final RpgClass CLERIC = new RpgClass(3, "CLERIC", Items.NETHER_STAR, BLESSED_BLADES, HOLY_SHIELD, SMITING_RAY, HEALING_AURA);

    public static RpgClass getById(int id) {
        switch (id) {
            case 0 -> { return MAGE; }
            case 1 -> { return ROGUE; }
            case 2 -> { return WARRIOR; }
            case 3 -> { return CLERIC; }
            default -> { return null; }
        }
    }

    public static RpgClass getByItem(Item item) {
        if (item.equals(MAGE.getClassItem())) {
            return MAGE;
        } else if (item.equals(ROGUE.getClassItem())) {
            return ROGUE;
        } else if (item.equals(WARRIOR.getClassItem())) {
            return WARRIOR;
        } else if (item.equals(CLERIC.getClassItem())) {
            return CLERIC;
        }
        return null;
    }
}
