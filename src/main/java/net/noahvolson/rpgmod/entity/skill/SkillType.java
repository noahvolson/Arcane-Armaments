package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.noahvolson.rpgmod.RpgMod;

public enum SkillType {

    // mage
    FIREBALL(       "fireball",        20,     0,  null,               new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_1.png")),
    BLINK(          "blink",           20,     0,  Items.ENDER_PEARL,          new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_2.png")),
    BLIZZARD(       "blizzard",        20,     0,  Items.POWDER_SNOW_BUCKET,   new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_3.png")),
    THUNDER(        "thunder",         20,     0,  Items.AMETHYST_SHARD,       new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_4.png")),

    // rogue
    ENVENOM(        "envenom",         20,     0,  null,               new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_1.png")),
    SMOKE_BOMB(     "smoke_bomb",      20,     0,  Items.GUNPOWDER,            new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_2.png")),
    RUPTURE(        "rupture",         20,     0,  Items.FERMENTED_SPIDER_EYE, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_3.png")),
    EXECUTE(        "execute",         20,     0,  Items.DIAMOND_HOE,          new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_4.png")),

    // warrior
    BERSERK(        "berserk",         100,    10, null,               new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_1.png")),
    STOMP(          "stomp",           20,     0,  Items.IRON_BOOTS,           new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_2.png")),
    GRAPPLE(        "grapple",         20,     0,  Items.FISHING_ROD,          new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_3.png")),
    MOLTEN_SHELL(   "molten_shell",    20,     0,  Items.BLAZE_POWDER,         new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_4.png")),

    // cleric
    BLESSED_BLADES( "blessed_blades",  20,     0,  null,               new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_1.png")),
    HOLY_SHIELD(    "holy_shield",     20,     0,  Items.BOOK,                 new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_2.png")),
    SMITING_RAY(    "smiting_ray",     20,     0,  Items.GLOW_BERRIES,         new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_3.png")),
    HEALING_AURA(   "healing_aura",    20,     0,  Items.GOLDEN_APPLE,         new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_4.png"));

    private final String label;
    private final String description;
    private final int cooldown;
    private final int turnoverCooldown;
    private final Item craftCost;
    private final ResourceLocation icon;

    private SkillType(String label, int cooldown, int turnoverCooldown, Item craftCost, ResourceLocation icon) {
        this.label = "skill_label.rpgmod." + label;
        this.description = "skill_description.rpgmod." + label;
        this.cooldown = cooldown;
        this.turnoverCooldown = turnoverCooldown;
        this.craftCost = craftCost;
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }
    public String getDescription() {
        return description;
    }
    public int getCooldown() {
        return cooldown;
    }
    public int getTurnoverCooldown() {
        return turnoverCooldown;
    }
    public Item getCraftCost() { return craftCost; }
    public ResourceLocation getIcon() {
        return icon;
    }
}