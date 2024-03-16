package net.noahvolson.rpgmod.rpgclass;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.item.ModItems;

import static net.noahvolson.rpgmod.entity.skill.SkillType.*;

public class RpgClasses {
    public static final RpgClass MAGE = new RpgClass(0, "MAGE", ModItems.MAGE_STAFF.get(), 13579059, FIREBALL, BLINK, BLIZZARD, THUNDER, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_background.png"));
    public static final RpgClass ROGUE = new RpgClass(1, "ROGUE", ModItems.ROGUE_DAGGER.get(), 10638799, ENVENOM, SMOKE_BOMB, RUPTURE, EXECUTE, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_background.png"));
    public static final RpgClass WARRIOR = new RpgClass(2, "WARRIOR", ModItems.WARRIOR_AXE.get(), 16732214, BERSERK, STOMP, GRAPPLE, MOLTEN_SHELL, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_background.png"));
    public static final RpgClass CLERIC = new RpgClass(3, "CLERIC", ModItems.CLERIC_SCEPTRE.get(), 15916663, BLESSED_BLADES, HOLY_SHIELD, SMITING_RAY, HEALING_AURA, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_background.png"));

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
