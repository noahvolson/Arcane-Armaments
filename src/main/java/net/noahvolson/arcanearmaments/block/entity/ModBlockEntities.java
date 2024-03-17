package net.noahvolson.arcanearmaments.block.entity;

import net.noahvolson.arcanearmaments.ArcaneArmaments;
import net.noahvolson.arcanearmaments.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ArcaneArmaments.MOD_ID);

    public static final RegistryObject<BlockEntityType<ArmoryBlockEntity>> ARMORY =
            BLOCK_ENTITIES.register("armory", () ->
                    BlockEntityType.Builder.of(ArmoryBlockEntity::new,
                            ModBlocks.ARMORY.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
