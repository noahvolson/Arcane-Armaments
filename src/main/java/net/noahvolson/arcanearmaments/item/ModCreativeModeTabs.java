package net.noahvolson.arcanearmaments.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.noahvolson.arcanearmaments.ArcaneArmaments;
import net.noahvolson.arcanearmaments.block.ModBlocks;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            ArcaneArmaments.MOD_ID);

    public static RegistryObject<CreativeModeTab> ARCANE_ARMAMENTS_TAB = CREATIVE_MODE_TABS.register("arcanearmaments_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.ARMORY.get().asItem()))
                    .title(Component.translatable("creativemodetab.arcanearmaments_tab")).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
