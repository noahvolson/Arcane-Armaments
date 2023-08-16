package net.noahvolson.rpgmod.entity.skill;

public enum SkillType {

    // mage
    FIREBALL(20, 0),
    BLINK(20, 0),
    BLIZZARD(20, 0),
    THUNDER(20, 0),

    // rogue
    SMOKE_BOMB(20, 0),
    VENOM_DAGGER(20, 0),
    RUPTURE_DAGGER(20, 0),
    EXECUTE_DAGGER(20, 0),

    // warrior
    BERSERK(100, 10),
    DEFIANCE(20, 0),
    MEATHOOK(20, 0),
    WARCRY(20, 0);

    private final int cooldown;
    private final int turnoverCooldown;

    private SkillType(int cooldown, int turnoverCooldown) {
        this.cooldown = cooldown;
        this.turnoverCooldown = turnoverCooldown;
    }

    public int getCooldown() {
        return cooldown;
    }
    public int getTurnoverCooldown() {
        return turnoverCooldown;
    }
}