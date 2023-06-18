package net.noahvolson.rpgmod.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.noahvolson.rpgmod.RpgMod;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, RpgMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> HIDDEN_PARTICLES =
            PARTICLE_TYPES.register("hidden_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> BLINK_PARTICLES =
            PARTICLE_TYPES.register("blink_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> FIREBOLT_PARTICLES =
            PARTICLE_TYPES.register("firebolt_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> ICEBOLT_PARTICLES =
            PARTICLE_TYPES.register("icebolt_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> FREEZE_PARTICLES =
            PARTICLE_TYPES.register("freeze_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> THUNDER_PARTICLES =
            PARTICLE_TYPES.register("thunder_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> RUNE_PARTICLES =
            PARTICLE_TYPES.register("rune_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> ZAPPED_PARTICLES =
            PARTICLE_TYPES.register("zapped_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> POISON_PARTICLES =
            PARTICLE_TYPES.register("poison_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> BLOOD_PARTICLES =
            PARTICLE_TYPES.register("blood_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> EXECUTE_PARTICLES =
            PARTICLE_TYPES.register("execute_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> DAGGER_PARTICLES =
            PARTICLE_TYPES.register("dagger_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
