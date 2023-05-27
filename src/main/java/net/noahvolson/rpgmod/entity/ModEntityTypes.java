package net.noahvolson.rpgmod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.entity.spell.FireBoltEntity;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RpgMod.MOD_ID);

    public static final RegistryObject<EntityType<FireBoltEntity>> FIRE_BOLT = ENTITY_TYPES.register("fire_bolt",
            () -> EntityType.Builder.of((EntityType.EntityFactory<FireBoltEntity>) FireBoltEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).build("fire_bolt"));
}
