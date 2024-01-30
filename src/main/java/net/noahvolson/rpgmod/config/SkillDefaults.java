package net.noahvolson.rpgmod.config;

public enum SkillDefaults {
    FIREBALL(       4, 0, 80, 100, 0),
    BLINK(          2, 0, 5, 100, 0),
    BLIZZARD(       3, 0, 120, 60, 0),
    THUNDER(        2, 0, 60, 1200, 0),

    // rogue
    ENVENOM(        0, 0, 0, 0, 0),
    SMOKE_BOMB(     0, 0, 0, 0, 0),
    RUPTURE(        0, 0, 0, 0, 0),
    EXECUTE(        0, 0, 0, 0, 0),

    // warrior
    BERSERK(        0, 0, 0, 0, 0),
    STOMP(          0, 0, 0, 0, 0),
    GRAPPLE(        0, 0, 0, 0, 0),
    MOLTEN_SHELL(   0, 0, 0, 0, 0),

    // cleric
    BLESSED_BLADES( 1, 1, 280,400, 0),
    HOLY_SHIELD(    0, 0, 600, 600, 0),
    SMITING_RAY(    8, 0, 100, 300, 0),
    HEALING_AURA(   0, 2, 200, 1200, 0);

    private final int damage;
    private final int healing;
    private final int duration;
    private final int cooldown;
    private final int turnoverCooldown;

    private SkillDefaults(int damage, int healing, int duration, int cooldown, int turnoverCooldown) {
        this.damage = damage;
        this.healing = healing;
        this.duration = duration;
        this.cooldown = cooldown;
        this.turnoverCooldown = turnoverCooldown;
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
