package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.noahvolson.rpgmod.RpgMod;

public enum SkillType {

    // mage
    FIREBALL(       "Fireball",     20,     0,  null,                   new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_1.png")),
    BLINK(          "Blink",        20,     0,  Items.ENDER_PEARL,              new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_2.png")),
    BLIZZARD(       "Blizzard",     20,     0,  Items.POWDER_SNOW_BUCKET,       new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_3.png")),
    THUNDER(        "Thunder",      20,     0,  Items.AMETHYST_SHARD,           new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_4.png")),

    // rogue
    VENOM_DAGGER(   "Envenom",      20,     0,  null,                   new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_1.png")),
    SMOKE_BOMB(     "Smoke Bomb",   20,     0,  Items.GUNPOWDER,                new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_2.png")),
    RUPTURE_DAGGER( "Rupture",      20,     0,  Items.FERMENTED_SPIDER_EYE,     new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_3.png")),
    EXECUTE_DAGGER( "Execute",      20,     0,  Items.DIAMOND_HOE,              new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_4.png")),

    // warrior
    BERSERK(        "Berserk",      100,    10, null,                   new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_1.png")),
    STOMP(          "Stomp",        20,     0,  Items.IRON_BOOTS,               new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_2.png")),
    GRAPPLE(        "Grapple",      20,     0,  Items.FISHING_ROD,              new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_3.png")),
    MOLTEN_SHELL(   "Molten Shell", 20,     0,  Items.BLAZE_POWDER,             new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_4.png")),

    // cleric
    BLESSED_BLADES( "Bless Blades", 20,     0,  null,                   new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_1.png")),
    HOLY_SHIELD(    "Holy Shield",  20,     0,  Items.BOOK,                     new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_2.png")),
    SMITING_RAY(    "Smiting Ray",  20,     0,  Items.GLOW_BERRIES,             new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_3.png")),
    HEALING_AURA(   "Heal Aura",    20,     0,  Items.GOLDEN_APPLE,             new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_4.png"));


    private final String label;
    private final int cooldown;
    private final int turnoverCooldown;
    private final Item craftCost;
    private final ResourceLocation icon;

    private SkillType(String label, int cooldown, int turnoverCooldown, Item craftCost, ResourceLocation icon) {
        this.label = label;
        this.cooldown = cooldown;
        this.turnoverCooldown = turnoverCooldown;
        this.craftCost = craftCost;
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
    public Item getCraftCost() { return craftCost; }
    public ResourceLocation getIcon() {
        return icon;
    }
}