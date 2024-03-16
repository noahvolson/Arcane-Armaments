package net.noahvolson.rpgmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.noahvolson.rpgmod.RpgMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RpgMod.MOD_ID);

    public static final RegistryObject<Item> ZIRCON = ITEMS.register("zircon",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPG_MOD_TAB)));

    public static final RegistryObject<Item> RAW_ZIRCON = ITEMS.register("raw_zircon",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPG_MOD_TAB)));

    public static final RegistryObject<Item> MAGE_STAFF = ITEMS.register("mage_staff",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPG_MOD_TAB)));

    public static final RegistryObject<Item> ROGUE_DAGGER = ITEMS.register("rogue_dagger",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPG_MOD_TAB)));

    public static final RegistryObject<Item> WARRIOR_AXE = ITEMS.register("warrior_axe",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPG_MOD_TAB)));

    public static final RegistryObject<Item> CLERIC_SCEPTRE = ITEMS.register("cleric_sceptre",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPG_MOD_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
