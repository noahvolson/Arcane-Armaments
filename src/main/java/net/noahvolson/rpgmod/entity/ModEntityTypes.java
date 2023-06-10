package net.noahvolson.rpgmod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.entity.spell.*;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RpgMod.MOD_ID);

    // mage spells
    public static final RegistryObject<EntityType<AbstractProjectileSpell>> BLINK = ENTITY_TYPES.register("blink",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileSpell>) BlinkSpell::new, MobCategory.MISC).sized(0.5F, 0.5F).build("blink"));

    public static final RegistryObject<EntityType<AbstractProjectileSpell>> FIREBALL = ENTITY_TYPES.register("fireball",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileSpell>) FireballSpell::new, MobCategory.MISC).sized(0.5F, 0.5F).build("fireball"));

    public static final RegistryObject<EntityType<AbstractProjectileSpell>> BLIZZARD = ENTITY_TYPES.register("blizzard",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileSpell>) BlizzardSpell::new, MobCategory.MISC).sized(0.5F, 0.5F).build("blizzard"));

    public static final RegistryObject<EntityType<AbstractProjectileSpell>> THUNDER = ENTITY_TYPES.register("thunder",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileSpell>) ThunderSpell::new, MobCategory.MISC).sized(0.5F, 0.5F).build("thunder"));

    // rogue spells
    public static final RegistryObject<EntityType<AbstractProjectileSpell>> DAGGER = ENTITY_TYPES.register("dagger",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileSpell>) DaggerAttack::new, MobCategory.MISC).sized(0.5F, 0.5F).build("dagger"));
}
