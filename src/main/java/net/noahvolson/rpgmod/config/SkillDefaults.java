package net.noahvolson.rpgmod.config;

public enum SkillDefaults {
    FIREBALL(       4, 0, 80, 100, 0),
    BLINK(          2, 0, 5, 100, 0),
    BLIZZARD(       3, 0, 120, 60, 0),
    THUNDER(        2, 0, 60, 800, 0),

    // rogue
    ENVENOM(        2, 0, 80, 20, 0),
    SMOKE_BOMB(     0, 0, 200, 220, 0),
    RUPTURE(        2, 0, 100, 600, 0),
    EXECUTE(        2, 0, 0, 100, 0),

    // warrior
    BERSERK(        4, 0, 200, 200, 20),
    STOMP(          0, 0, 30, 200, 0),
    GRAPPLE(        3, 0, 0, 140, 0),
    MOLTEN_SHELL(   0, 4, 60, 600, 240),

    // cleric
    BLESSED_BLADES( 1, 1, 280,400, 0),
    HOLY_SHIELD(    0, 0, 600, 600, 0),
    SMITING_RAY(    8, 0, 100, 300, 0),
    HEALING_AURA(   0, 2, 200, 700, 0);

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
