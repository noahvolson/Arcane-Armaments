package net.noahvolson.arcanearmaments.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
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
