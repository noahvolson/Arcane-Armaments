package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.resources.ResourceLocation;
import net.noahvolson.rpgmod.RpgMod;

public enum SkillType {

    // mage
    FIREBALL(       "Fireball",20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_1.png")),
    BLINK(          "Blink",20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_2.png")),
    BLIZZARD(       "Blizzard",20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_3.png")),
    THUNDER(        "Thunder",20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_4.png")),

    // rogue
    VENOM_DAGGER(   "Envenom",20, 0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_1.png")),
    SMOKE_BOMB(     "Smoke Bomb",20, 0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_2.png")),
    RUPTURE_DAGGER( "Rupture",20, 0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_3.png")),
    EXECUTE_DAGGER( "Execute",20, 0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_4.png")),

    // warrior
    BERSERK(        "Berserk",100,    10, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_1.png")),
    STOMP(          "Stomp",20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_2.png")),
    GRAPPLE(        "Grapple",20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_3.png")),
    MOLTEN_SHELL(   "Molten Shell",20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_4.png")),

    // cleric
    BLESSED_BLADES( "Bless Blades",20, 0, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_1.png")),
    HOLY_SHIELD(    "Holy Shield",20, 0, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_2.png")),
    SMITING_RAY(    "Smiting Ray",20, 0, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_3.png")),
    HEALING_AURA(   "Heal Aura",20, 0, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_4.png"));


    private final String label;
    private final int cooldown;
    private final int turnoverCooldown;
    private final ResourceLocation icon;

    private SkillType(String label, int cooldown, int turnoverCooldown, ResourceLocation icon) {
        this.label = label;
        this.cooldown = cooldown;
        this.turnoverCooldown = turnoverCooldown;
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }
    public int getCooldown() {
        return cooldown;
    }
    public int getTurnoverCooldown() {
        return turnoverCooldown;
    }
    public ResourceLocation getIcon() {
        return icon;
    }
}