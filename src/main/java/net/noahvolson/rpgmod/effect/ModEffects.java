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

    public static final RegistryObject<MobEffect> BERSERK = MOB_EFFECTS.register("berserk",
            () -> new BerserkEffect(MobEffectCategory.BENEFICIAL, 16755263));

    public static final RegistryObject<MobEffect> STOMPING = MOB_EFFECTS.register("stomping",
            () -> new StompingEffect(MobEffectCategory.HARMFUL, 0));

    public static final RegistryObject<MobEffect> STUNNED = MOB_EFFECTS.register("stunned",
            () -> new StunEffect(MobEffectCategory.HARMFUL, 0));

    public static final RegistryObject<MobEffect> SHELL = MOB_EFFECTS.register("shell",
            () -> new ShellEffect(MobEffectCategory.HARMFUL, 0));

    public static final RegistryObject<MobEffect> BLESSED_BLADE = MOB_EFFECTS.register("blessed_blade",
            () -> new BlessedBladeEffect(MobEffectCategory.HARMFUL, 0));

    public static final RegistryObject<MobEffect> HOLY_SHIELD_3 = MOB_EFFECTS.register("holy_shield_3",
            () -> new HolyShieldEffect(MobEffectCategory.BENEFICIAL, 0));

    public static final RegistryObject<MobEffect> HOLY_SHIELD_2 = MOB_EFFECTS.register("holy_shield_2",
            () -> new HolyShieldEffect(MobEffectCategory.BENEFICIAL, 0));

    public static final RegistryObject<MobEffect> HOLY_SHIELD_1 = MOB_EFFECTS.register("holy_shield_1",
            () -> new HolyShieldEffect(MobEffectCategory.BENEFICIAL, 0));

    public static final RegistryObject<MobEffect> SMITING = MOB_EFFECTS.register("smiting",
            () -> new SmitingEffect(MobEffectCategory.HARMFUL, 0));

    public static final RegistryObject<MobEffect> HEALING_AURA = MOB_EFFECTS.register("healing_aura",
            () -> new HealingAuraEffect(MobEffectCategory.BENEFICIAL, 0));

    public static final RegistryObject<MobEffect> COOLDOWN_1 = MOB_EFFECTS.register("cooldown_1",
            () -> new EmptyEffect(MobEffectCategory.BENEFICIAL));

    public static final RegistryObject<MobEffect> COOLDOWN_2 = MOB_EFFECTS.register("cooldown_2",
            () -> new EmptyEffect(MobEffectCategory.BENEFICIAL));

    public static final RegistryObject<MobEffect> COOLDOWN_3 = MOB_EFFECTS.register("cooldown_3",
            () -> new EmptyEffect(MobEffectCategory.BENEFICIAL));

    public static final RegistryObject<MobEffect> COOLDOWN_4 = MOB_EFFECTS.register("cooldown_4",
            () -> new EmptyEffect(MobEffectCategory.BENEFICIAL));

    public static final RegistryObject<MobEffect> COOLDOWN_5 = MOB_EFFECTS.register("cooldown_5",
            () -> new EmptyEffect(MobEffectCategory.BENEFICIAL));

    public static final RegistryObject<MobEffect> COOLDOWN_6 = MOB_EFFECTS.register("cooldown_6",
            () -> new EmptyEffect(MobEffectCategory.BENEFICIAL));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}