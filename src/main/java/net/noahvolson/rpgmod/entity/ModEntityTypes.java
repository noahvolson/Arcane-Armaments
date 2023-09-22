package net.noahvolson.rpgmod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.entity.skill.*;
import net.noahvolson.rpgmod.entity.skill.mage.BlinkSpell;
import net.noahvolson.rpgmod.entity.skill.mage.BlizzardSpell;
import net.noahvolson.rpgmod.entity.skill.mage.FireballSpell;
import net.noahvolson.rpgmod.entity.skill.mage.ThunderSpell;
import net.noahvolson.rpgmod.entity.skill.rogue.ExecuteDaggerAttack;
import net.noahvolson.rpgmod.entity.skill.rogue.RuptureDaggerAttack;
import net.noahvolson.rpgmod.entity.skill.rogue.VenomDaggerAttack;
import net.noahvolson.rpgmod.entity.skill.warrior.BerserkAttack;
import net.noahvolson.rpgmod.entity.skill.warrior.GrappleAttack;

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
    public static final RegistryObject<EntityType<AbstractProjectileAbility>> VENOM_DAGGER = ENTITY_TYPES.register("venom_dagger",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileAbility>) VenomDaggerAttack::new, MobCategory.MISC).sized(0.5F, 0.5F).build("poison_dagger"));

    public static final RegistryObject<EntityType<AbstractProjectileAbility>> RUPTURE_DAGGER = ENTITY_TYPES.register("rupture_dagger",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileAbility>) RuptureDaggerAttack::new, MobCategory.MISC).sized(0.5F, 0.5F).build("rupture_dagger"));

    public static final RegistryObject<EntityType<AbstractProjectileAbility>> EXECUTE_DAGGER = ENTITY_TYPES.register("execute_dagger",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileAbility>) ExecuteDaggerAttack::new, MobCategory.MISC).sized(0.5F, 0.5F).build("execute_dagger"));

    // warrior spells
    public static final RegistryObject<EntityType<AbstractProjectileAbility>> BERSERK = ENTITY_TYPES.register("berserk",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileAbility>) BerserkAttack::new, MobCategory.MISC).sized(0.5F, 0.5F).build("berserk"));

    public static final RegistryObject<EntityType<GrappleAttack>> MEATHOOK = ENTITY_TYPES.register("meathook",
            () -> EntityType.Builder.of((EntityType.EntityFactory<GrappleAttack>) GrappleAttack::new, MobCategory.MISC).sized(1.2F, 1.2F).build("meathook"));
}
