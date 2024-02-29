package net.noahvolson.rpgmod.config;

import net.minecraft.world.damagesource.DamageSource;

public class ModDamageSource {
    public static final DamageSource RUPTURE = new DamageSource("rupture").bypassArmor().bypassEnchantments();
    public static final DamageSource VENOM = new DamageSource("venom").bypassArmor().bypassEnchantments();
    public static final DamageSource EXECUTE = new DamageSource("execute").bypassArmor().bypassEnchantments();
    public static final DamageSource DAGGER = new DamageSource("dagger").bypassArmor().bypassEnchantments();

    public static final DamageSource SMITING = new DamageSource("smiting").bypassArmor().bypassEnchantments();
    public static final DamageSource SMITING_RAY = new DamageSource("smiting_ray").bypassArmor().bypassEnchantments();

    public static final DamageSource BLINK = new DamageSource("blink").bypassArmor().bypassEnchantments();
    public static final DamageSource FIREBALL = new DamageSource("fireball").bypassArmor().bypassEnchantments();
    public static final DamageSource ZAP = new DamageSource("zap").bypassArmor().bypassEnchantments();

    public static final DamageSource DECAPITATE = new DamageSource("decapitate").bypassArmor().bypassEnchantments();
    public static final DamageSource GRAPPLE = new DamageSource("grapple").bypassArmor().bypassEnchantments();
    public static final DamageSource STOMP = new DamageSource("stomp").bypassArmor().bypassEnchantments();

}
