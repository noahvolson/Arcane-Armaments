package net.noahvolson.arcanearmaments.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.noahvolson.arcanearmaments.block.ModBlocks;

public class ModCreativeModeTab {
    public static final CreativeModeTab RPG_MOD_TAB = new CreativeModeTab("arcanearmaments_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.ARMORY.get().asItem());
        }
    };
}
