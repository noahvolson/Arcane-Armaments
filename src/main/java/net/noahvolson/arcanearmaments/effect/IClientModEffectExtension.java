package net.noahvolson.arcanearmaments.effect;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

public class IClientModEffectExtension implements IClientMobEffectExtensions {

    public boolean visibleInInventory = false;

    public IClientModEffectExtension (boolean visibleInInventory) {
        this.visibleInInventory = visibleInInventory;
    }

    @Override
    public boolean isVisibleInInventory(MobEffectInstance instance)
    {
        return visibleInInventory;
    }
}
