package net.noahvolson.rpgmod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.noahvolson.rpgmod.block.ModBlocks;

public class ModCreativeModeTab {
    public static final CreativeModeTab RPG_MOD_TAB = new CreativeModeTab("rpgmodtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.ARMORY.get().asItem());
        }
    };
}
