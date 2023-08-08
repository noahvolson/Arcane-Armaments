package net.noahvolson.rpgmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EmptyEffect extends MobEffect {
    public EmptyEffect(MobEffectCategory mobEffectCategory) {
        super(mobEffectCategory, 16777215);
    }
}
