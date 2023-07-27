package net.noahvolson.rpgmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.noahvolson.rpgmod.RpgMod;

public class ModEffects {
    public static DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, RpgMod.MOD_ID);

    public static final RegistryObject<MobEffect> FREEZE = MOB_EFFECTS.register("freeze",
            () -> new FreezeEffect(MobEffectCategory.HARMFUL, 3124687));

    public static final RegistryObject<MobEffect> ZAPPED = MOB_EFFECTS.register("zapped",
            () -> new ZappedEffect(MobEffectCategory.HARMFUL, 8659406));

    public static final RegistryObject<MobEffect> RUPTURED = MOB_EFFECTS.register("rupture",
            () -> new RuptureEffect(MobEffectCategory.HARMFUL, 13050139));

    public static final RegistryObject<MobEffect> VENOM = MOB_EFFECTS.register("venom",
            () -> new VenomEffect(MobEffectCategory.HARMFUL, 10281744));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
