package net.noahvolson.rpgmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import org.jetbrains.annotations.NotNull;

public class EmptyEffect extends MobEffect {

    public EmptyEffect(MobEffectCategory mobEffectCategory) {
        super(mobEffectCategory, 16777215);
    }

    @Override
    public @NotNull Object getEffectRendererInternal() {
        return new IClientModEffectExtension(false);
    }
}
