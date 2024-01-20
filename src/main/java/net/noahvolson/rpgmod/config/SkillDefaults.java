package net.noahvolson.rpgmod.config;

public enum SkillDefaults {
    FIREBALL(       0, 0, 0, 0, 0, 0),
    BLINK(          0, 0, 0, 0, 0, 0),
    BLIZZARD(       0, 0, 0, 0, 0, 0),
    THUNDER(        0, 0, 0, 0, 0, 0),

    // rogue
    ENVENOM(        0, 0, 0, 0, 0, 0),
    SMOKE_BOMB(     0, 0, 0, 0, 0, 0),
    RUPTURE(        0, 0, 0, 0, 0, 0),
    EXECUTE(        0, 0, 0, 0, 0, 0),

    // warrior
    BERSERK(        0, 0, 0, 0, 0, 0),
    STOMP(          0, 0, 0, 0, 0, 0),
    GRAPPLE(        0, 0, 0, 0, 0, 0),
    MOLTEN_SHELL(   0, 0, 0, 0, 0, 0),

    // cleric
    BLESSED_BLADES( 0, 0, 0, 0, 0, 0),
    HOLY_SHIELD(    0, 0, 0, 0, 0, 0),
    SMITING_RAY(    0, 0, 0, 0, 0, 0),
    HEALING_AURA(   0, 0, 0, 0, 0, 0);

    private final int damage;
    private final int healing;
    private final int duration;
    private final int effectProcs;
    private final int cooldown;
    private final int turnoverCooldown;

    private SkillDefaults(int damage, int healing, int duration, int effectProcs, int cooldown, int turnoverCooldown) {
        this.damage = damage;
        this.healing = healing;
        this.duration = duration;
        this.effectProcs = effectProcs;
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