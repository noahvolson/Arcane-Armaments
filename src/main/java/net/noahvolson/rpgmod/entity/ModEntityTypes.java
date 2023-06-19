package net.noahvolson.rpgmod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.entity.skill.*;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RpgMod.MOD_ID);

    // mage spells
    public static final RegistryObject<EntityType<AbstractProjectileAbility>> BLINK = ENTITY_TYPES.register("blink",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileAbility>) BlinkSpell::new, MobCategory.MISC).sized(0.5F, 0.5F).build("blink"));

    public static final RegistryObject<EntityType<AbstractProjectileAbility>> FIREBALL = ENTITY_TYPES.register("fireball",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileAbility>) FireballSpell::new, MobCategory.MISC).sized(0.5F, 0.5F).build("fireball"));

    public static final RegistryObject<EntityType<AbstractProjectileAbility>> BLIZZARD = ENTITY_TYPES.register("blizzard",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileAbility>) BlizzardSpell::new, MobCategory.MISC).sized(0.5F, 0.5F).build("blizzard"));

    public static final RegistryObject<EntityType<AbstractProjectileAbility>> THUNDER = ENTITY_TYPES.register("thunder",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileAbility>) ThunderSpell::new, MobCategory.MISC).sized(0.5F, 0.5F).build("thunder"));

    // rogue spells
    public static final RegistryObject<EntityType<AbstractProjectileAbility>> POISON_DAGGER = ENTITY_TYPES.register("poison_dagger",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileAbility>) PoisonDaggerAttack::new, MobCategory.MISC).sized(0.5F, 0.5F).build("poison_dagger"));

    public static final RegistryObject<EntityType<AbstractProjectileAbility>> RUPTURE_DAGGER = ENTITY_TYPES.register("rupture_dagger",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileAbility>) RuptureDaggerAttack::new, MobCategory.MISC).sized(0.5F, 0.5F).build("rupture_dagger"));

    public static final RegistryObject<EntityType<AbstractProjectileAbility>> EXECUTE_DAGGER = ENTITY_TYPES.register("execute_dagger",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileAbility>) ExecuteDaggerAttack::new, MobCategory.MISC).sized(0.5F, 0.5F).build("execute_dagger"));
}
