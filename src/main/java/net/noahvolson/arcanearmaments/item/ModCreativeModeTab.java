package net.noahvolson.arcanearmaments.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noahvolson.arcanearmaments.ArcaneArmaments;
import net.noahvolson.arcanearmaments.block.ModBlocks;

@Mod.EventBusSubscriber(modid = ArcaneArmaments.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTab {
    public static CreativeModeTab ARCANE_ARMAMENTS_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        ARCANE_ARMAMENTS_TAB = event.registerCreativeModeTab(new ResourceLocation(ArcaneArmaments.MOD_ID, "arcanearmaments_tab"),
                builder -> builder.icon(() -> new ItemStack(ModBlocks.ARMORY.get().asItem())).title(Component.literal("Arcane Armaments")).build());
    }
}
