package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.config.OptionsHolder;
import net.noahvolson.rpgmod.config.SkillConfig;

public enum SkillType {

    // mage
    FIREBALL(       "fireball",         OptionsHolder.COMMON.mageConfig.sk1(),      null,               new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_1.png")),
    BLINK(          "blink",            OptionsHolder.COMMON.mageConfig.sk2(),      Items.ENDER_PEARL,          new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_2.png")),
    BLIZZARD(       "blizzard",         OptionsHolder.COMMON.mageConfig.sk3(),      Items.POWDER_SNOW_BUCKET,   new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_3.png")),
    THUNDER(        "thunder",          OptionsHolder.COMMON.mageConfig.sk4(),      Items.AMETHYST_SHARD,       new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_4.png")),

    // rogue
    ENVENOM(        "envenom",          OptionsHolder.COMMON.rogueConfig.sk1(),     null,               new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_1.png")),
    SMOKE_BOMB(     "smoke_bomb",       OptionsHolder.COMMON.rogueConfig.sk2(),     Items.GUNPOWDER,            new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_2.png")),
    RUPTURE(        "rupture",          OptionsHolder.COMMON.rogueConfig.sk3(),     Items.FERMENTED_SPIDER_EYE, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_3.png")),
    EXECUTE(        "execute",          OptionsHolder.COMMON.rogueConfig.sk4(),     Items.DIAMOND_HOE,          new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_4.png")),

    // warrior
    BERSERK(        "berserk",          OptionsHolder.COMMON.warriorConfig.sk1(),   null,               new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_1.png")),
    STOMP(          "stomp",            OptionsHolder.COMMON.warriorConfig.sk2(),   Items.IRON_BOOTS,           new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_2.png")),
    GRAPPLE(        "grapple",          OptionsHolder.COMMON.warriorConfig.sk3(),   Items.FISHING_ROD,          new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_3.png")),
    MOLTEN_SHELL(   "molten_shell",     OptionsHolder.COMMON.warriorConfig.sk4(),   Items.BLAZE_POWDER,         new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_4.png")),

    // cleric
    BLESSED_BLADES( "blessed_blades",   OptionsHolder.COMMON.clericConfig.sk1(),    null,               new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_1.png")),
    HOLY_SHIELD(    "holy_shield",      OptionsHolder.COMMON.clericConfig.sk2(),    Items.BOOK,                 new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_2.png")),
    SMITING_RAY(    "smiting_ray",      OptionsHolder.COMMON.clericConfig.sk3(),    Items.GLOW_BERRIES,         new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_3.png")),
    HEALING_AURA(   "healing_aura",     OptionsHolder.COMMON.clericConfig.sk4(),    Items.GOLDEN_APPLE,         new ResourceLocation(RpgMod.MOD_ID, "textures/gui/cleric/cleric_4.png"));

    private final String label;
    private final String description;
    private final Item craftCost;
    private final ResourceLocation icon;

    private final int damage;
    private final int healing;
    private final int duration;
    private final int effectProcs;
    private final int cooldown;
    private final int turnoverCooldown;

    private SkillType(String label, SkillConfig config, Item craftCost, ResourceLocation icon) {
        // Non-configurable
        this.label = "skill_label.rpgmod." + label;
        this.description = "skill_description.rpgmod." + label;
        this.craftCost = craftCost;
        this.icon = icon;

        // Configurable
        this.damage = config.damage().get();
        this.healing = config.healing().get();
        this.duration = config.duration().get();
        this.effectProcs = config.effectProcs().get();
        this.cooldown = config.cooldown().get();
        this.turnoverCooldown = config.turnoverCooldown().get();
    }

    public String getLabel() {
        return label;
    }
    public String getDescription() {
        return description;
    }
    public Item getCraftCost() { return craftCost; }
    public ResourceLocation getIcon() {
        return icon;
    }

    public int getDamage() {
        return damage;
    }
    public int getHealing() {
        return healing;
    }
    public int getDuration() {
        return duration;
    }
    public int getEffectProcs() {
        return effectProcs;
    }
    public int getCooldown() {
        return cooldown;
    }
    public int getTurnoverCooldown() {
        return turnoverCooldown;
    }
}