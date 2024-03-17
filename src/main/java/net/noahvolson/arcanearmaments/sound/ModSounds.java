package net.noahvolson.arcanearmaments.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.noahvolson.arcanearmaments.ArcaneArmaments;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ArcaneArmaments.MOD_ID);

    public static final RegistryObject<SoundEvent> BLIZZARD_AREA = registerSoundEvent("blizzard_area");
    public static final RegistryObject<SoundEvent> BLIZZARD_CAST = registerSoundEvent("blizzard_cast");
    public static final RegistryObject<SoundEvent> BLIZZARD_IMPACT = registerSoundEvent("blizzard_impact");

    public static final RegistryObject<SoundEvent> THUNDER_CAST = registerSoundEvent("thunder_cast");
    public static final RegistryObject<SoundEvent> THUNDER_IMPACT = registerSoundEvent("thunder_impact");
    public static final RegistryObject<SoundEvent> THUNDER_PULSE = registerSoundEvent("thunder_pulse");

    public static final RegistryObject<SoundEvent> VENOM_DAGGER = registerSoundEvent("venom_dagger");
    public static final RegistryObject<SoundEvent> RUPTURE_DAGGER = registerSoundEvent("rupture_dagger");
    public static final RegistryObject<SoundEvent> EXECUTE_DAGGER = registerSoundEvent("execute_dagger");
    public static final RegistryObject<SoundEvent> SMOKE_BOMB = registerSoundEvent("smoke_bomb");
    public static final RegistryObject<SoundEvent> FAILED_EXECUTE = registerSoundEvent("failed_execute");

    public static final RegistryObject<SoundEvent> BERSERK = registerSoundEvent("berserk");
    public static final RegistryObject<SoundEvent> GRAPPLE_CAST = registerSoundEvent("grapple_cast");
    public static final RegistryObject<SoundEvent> GRAPPLE_GROUND = registerSoundEvent("grapple_ground");
    public static final RegistryObject<SoundEvent> STOMP_IMPACT = registerSoundEvent("stomp_impact");
    public static final RegistryObject<SoundEvent> MOLTEN_SHELL = registerSoundEvent("molten_shell");

    public static final RegistryObject<SoundEvent> HOLY_SHIELD_CAST = registerSoundEvent("holy_shield_cast");
    public static final RegistryObject<SoundEvent> HOLY_SHIELD_IMPACT = registerSoundEvent("holy_shield_impact");
    public static final RegistryObject<SoundEvent> SMITING_RAY_CAST = registerSoundEvent("smiting_ray_cast");
    public static final RegistryObject<SoundEvent> SMITING_RAY_IMPACT = registerSoundEvent("smiting_ray_impact");
    public static final RegistryObject<SoundEvent> HEALING_AURA = registerSoundEvent("healing_aura");


    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(ArcaneArmaments.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}