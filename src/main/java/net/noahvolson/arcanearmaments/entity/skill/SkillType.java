package net.noahvolson.arcanearmaments.entity.skill;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.noahvolson.arcanearmaments.ArcaneArmaments;
import net.noahvolson.arcanearmaments.config.OptionsHolder;
import net.noahvolson.arcanearmaments.config.SkillConfig;

public enum SkillType {

    // mage
    FIREBALL(       "fireball",         OptionsHolder.COMMON.mageConfig.sk1(),      null,               new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/mage/mage_1.png")),
    BLINK(          "blink",            OptionsHolder.COMMON.mageConfig.sk2(),      Items.ENDER_PEARL,          new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/mage/mage_2.png")),
    BLIZZARD(       "blizzard",         OptionsHolder.COMMON.mageConfig.sk3(),      Items.POWDER_SNOW_BUCKET,   new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/mage/mage_3.png")),
    THUNDER(        "thunder",          OptionsHolder.COMMON.mageConfig.sk4(),      Items.AMETHYST_SHARD,       new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/mage/mage_4.png")),

    // rogue
    ENVENOM(        "envenom",          OptionsHolder.COMMON.rogueConfig.sk1(),     null,               new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/rogue/rogue_1.png")),
    SMOKE_BOMB(     "smoke_bomb",       OptionsHolder.COMMON.rogueConfig.sk2(),     Items.GUNPOWDER,            new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/rogue/rogue_2.png")),
    RUPTURE(        "rupture",          OptionsHolder.COMMON.rogueConfig.sk3(),     Items.FERMENTED_SPIDER_EYE, new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/rogue/rogue_3.png")),
    EXECUTE(        "execute",          OptionsHolder.COMMON.rogueConfig.sk4(),     Items.DIAMOND_HOE,          new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/rogue/rogue_4.png")),

    // warrior
    BERSERK(        "berserk",          OptionsHolder.COMMON.warriorConfig.sk1(),   null,               new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/warrior/warrior_1.png")),
    STOMP(          "stomp",            OptionsHolder.COMMON.warriorConfig.sk2(),   Items.IRON_BOOTS,           new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/warrior/warrior_2.png")),
    GRAPPLE(        "grapple",          OptionsHolder.COMMON.warriorConfig.sk3(),   Items.FISHING_ROD,          new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/warrior/warrior_3.png")),
    MOLTEN_SHELL(   "molten_shell",     OptionsHolder.COMMON.warriorConfig.sk4(),   Items.BLAZE_POWDER,         new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/warrior/warrior_4.png")),

    // cleric
    BLESSED_BLADES( "blessed_blades",   OptionsHolder.COMMON.clericConfig.sk1(),    null,               new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/cleric/cleric_1.png")),
    HOLY_SHIELD(    "holy_shield",      OptionsHolder.COMMON.clericConfig.sk2(),    Items.BOOK,                 new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/cleric/cleric_2.png")),
    SMITING_RAY(    "smiting_ray",      OptionsHolder.COMMON.clericConfig.sk3(),    Items.GLOW_BERRIES,         new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/cleric/cleric_3.png")),
    HEALING_AURA(   "healing_aura",     OptionsHolder.COMMON.clericConfig.sk4(),    Items.GOLDEN_APPLE,         new ResourceLocation(ArcaneArmaments.MOD_ID, "textures/gui/cleric/cleric_4.png"));

    private final String label;
    private final String description;
    private final Item craftCost;
    private final ResourceLocation icon;

    private final int damage;
    private final int healing;
    private final int duration;
    private final int cooldown;
    private final int turnoverCooldown;

    private SkillType(String label, SkillConfig config, Item craftCost, ResourceLocation icon) {
        // Non-configurable
        this.label = "skill_label.arcanearmaments." + label;
        this.description = "skill_description.arcanearmaments." + label;
        this.craftCost = craftCost;
        this.icon = icon;

        // Configurable
        this.damage = config.damage() != null ? config.damage().get() : 0;
        this.healing = config.healing() != null ? config.healing().get() : 0;
        this.duration = config.duration() != null ? config.duration().get() : 0;
        this.cooldown = config.cooldown().get() != null ? config.cooldown().get() : 0;
        this.turnoverCooldown = config.turnoverCooldown() != null ? config.turnoverCooldown().get() : 0;
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
    public int getCooldown() {
        return cooldown;
    }
    public int getTurnoverCooldown() {
        return turnoverCooldown;
    }
}