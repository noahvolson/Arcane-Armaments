package net.noahvolson.rpgmod.block.entity;

import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RpgMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<ArmoryBlockEntity>> ARMORY =
            BLOCK_ENTITIES.register("armory", () ->
                    BlockEntityType.Builder.of(ArmoryBlockEntity::new,
                            ModBlocks.ARMORY.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
