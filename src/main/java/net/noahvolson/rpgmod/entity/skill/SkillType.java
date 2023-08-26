package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.resources.ResourceLocation;
import net.noahvolson.rpgmod.RpgMod;

public enum SkillType {

    // mage
    FIREBALL(   20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_1.png")),
    BLINK(      20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_2.png")),
    BLIZZARD(   20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_3.png")),
    THUNDER(    20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/mage/mage_4.png")),

    // rogue
    VENOM_DAGGER(   20, 0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_1.png")),
    SMOKE_BOMB(     20, 0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_2.png")),
    RUPTURE_DAGGER( 20, 0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_3.png")),
    EXECUTE_DAGGER( 20, 0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/rogue/rogue_4.png")),

    // warrior
    BERSERK(    100,    10, new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_1.png")),
    WARCRY(     20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_2.png")),
    MEATHOOK(   20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_3.png")),
    DEFIANCE(   20,     0,  new ResourceLocation(RpgMod.MOD_ID, "textures/gui/warrior/warrior_4.png"));

    private final int cooldown;
    private final int turnoverCooldown;
    private final ResourceLocation icon;

    private SkillType(int cooldown, int turnoverCooldown, ResourceLocation icon) {
        this.cooldown = cooldown;
        this.turnoverCooldown = turnoverCooldown;
        this.icon = icon;
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