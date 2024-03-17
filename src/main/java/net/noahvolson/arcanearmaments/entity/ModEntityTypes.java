package net.noahvolson.arcanearmaments.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.noahvolson.arcanearmaments.ArcaneArmaments;
import net.noahvolson.arcanearmaments.entity.skill.*;
import net.noahvolson.arcanearmaments.entity.skill.cleric.SmitingRaySkill;
import net.noahvolson.arcanearmaments.entity.skill.mage.BlinkSpell;
import net.noahvolson.arcanearmaments.entity.skill.mage.BlizzardSpell;
import net.noahvolson.arcanearmaments.entity.skill.mage.FireballSpell;
import net.noahvolson.arcanearmaments.entity.skill.mage.ThunderSpell;
import net.noahvolson.arcanearmaments.entity.skill.rogue.ExecuteDaggerAttack;
import net.noahvolson.arcanearmaments.entity.skill.rogue.RuptureDaggerAttack;
import net.noahvolson.arcanearmaments.entity.skill.rogue.VenomDaggerAttack;
import net.noahvolson.arcanearmaments.entity.skill.warrior.BerserkAttack;
import net.noahvolson.arcanearmaments.entity.skill.warrior.GrappleAttack;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ArcaneArmaments.MOD_ID);

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

    public static final RegistryObject<EntityType<GrappleAttack>> GRAPPLE = ENTITY_TYPES.register("grapple",
            () -> EntityType.Builder.of((EntityType.EntityFactory<GrappleAttack>) GrappleAttack::new, MobCategory.MISC).sized(1.2F, 1.2F).build("meathook"));

    // cleric spells
    public static final RegistryObject<EntityType<AbstractProjectileAbility>> SMITING_RAY = ENTITY_TYPES.register("smiting_ray",
            () -> EntityType.Builder.of((EntityType.EntityFactory<AbstractProjectileAbility>) SmitingRaySkill::new, MobCategory.MISC).sized(0.7F, 0.7F).build("smiting_ray"));

}
