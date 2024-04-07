package net.noahvolson.arcanearmaments.damage;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class ModDamageSources {

    private final Registry<DamageType> damageTypes;

    private final DamageSource rupture;
    private final DamageSource venom;
    private final DamageSource execute;
    private final DamageSource dagger;
    private final DamageSource smiting;
    private final DamageSource smiting_ray;
    private final DamageSource blink;
    private final DamageSource fireball;
    private final DamageSource zap;
    private final DamageSource decapitate;
    private final DamageSource grapple;
    private final DamageSource stomp;

    public ModDamageSources(RegistryAccess pRegistry) {
        this.damageTypes = pRegistry.registryOrThrow(Registries.DAMAGE_TYPE);
        this.rupture = this.source(ModDamageTypes.RUPTURE);
        this.venom = this.source(ModDamageTypes.VENOM);
        this.execute = this.source(ModDamageTypes.EXECUTE);
        this.dagger = this.source(ModDamageTypes.DAGGER);
        this.smiting = this.source(ModDamageTypes.SMITING);
        this.smiting_ray = this.source(ModDamageTypes.SMITING_RAY);
        this.blink = this.source(ModDamageTypes.BLINK);
        this.fireball = this.source(ModDamageTypes.FIREBALL);
        this.zap = this.source(ModDamageTypes.ZAP);
        this.decapitate = this.source(ModDamageTypes.DECAPITATE);
        this.grapple = this.source(ModDamageTypes.GRAPPLE);
        this.stomp = this.source(ModDamageTypes.STOMP);
    }

    private DamageSource source(ResourceKey<DamageType> pDamageTypeKey) {
        return new DamageSource(this.damageTypes.getHolderOrThrow(pDamageTypeKey));
    }

    private DamageSource source(ResourceKey<DamageType> pDamageTypeKey, @Nullable Entity pEntity) {
        return new DamageSource(this.damageTypes.getHolderOrThrow(pDamageTypeKey), pEntity);
    }

    private DamageSource source(ResourceKey<DamageType> pDamageTypeKey, @Nullable Entity pCausingEntity, @Nullable Entity pDirectEntity) {
        return new DamageSource(this.damageTypes.getHolderOrThrow(pDamageTypeKey), pCausingEntity, pDirectEntity);
    }

    public DamageSource rupture() { return this.rupture; }
    public DamageSource venom() { return this.venom; }
    public DamageSource execute() { return this.execute; }
    public DamageSource dagger() { return this.dagger; }
    public DamageSource smiting() { return this.smiting; }
    public DamageSource smiting_ray() { return this.smiting_ray; }
    public DamageSource blink() { return this.blink; }
    public DamageSource fireball() { return this.fireball; }
    public DamageSource zap() { return this.zap; }
    public DamageSource decapitate() { return this.decapitate; }
    public DamageSource grapple() { return this.grapple; }
    public DamageSource stomp() { return this.stomp; }

}