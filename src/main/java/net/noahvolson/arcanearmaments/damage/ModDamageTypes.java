package net.noahvolson.arcanearmaments.damage;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.noahvolson.arcanearmaments.ArcaneArmaments;

public class ModDamageTypes {

    public static final ResourceKey<DamageType> RUPTURE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArcaneArmaments.MOD_ID, "rupture"));
    public static final ResourceKey<DamageType> VENOM = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArcaneArmaments.MOD_ID, "venom"));
    public static final ResourceKey<DamageType> EXECUTE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArcaneArmaments.MOD_ID, "execute"));
    public static final ResourceKey<DamageType> DAGGER = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArcaneArmaments.MOD_ID, "dagger"));

    public static final ResourceKey<DamageType> SMITING = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArcaneArmaments.MOD_ID, "smiting"));
    public static final ResourceKey<DamageType> SMITING_RAY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArcaneArmaments.MOD_ID, "smiting_ray"));

    public static final ResourceKey<DamageType> BLINK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArcaneArmaments.MOD_ID, "blink"));
    public static final ResourceKey<DamageType> FIREBALL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArcaneArmaments.MOD_ID, "fireball"));
    public static final ResourceKey<DamageType> ZAP = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArcaneArmaments.MOD_ID, "zap"));

    public static final ResourceKey<DamageType> DECAPITATE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArcaneArmaments.MOD_ID, "decapitate"));
    public static final ResourceKey<DamageType> GRAPPLE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArcaneArmaments.MOD_ID, "grapple"));
    public static final ResourceKey<DamageType> STOMP = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArcaneArmaments.MOD_ID, "stomp"));
}
