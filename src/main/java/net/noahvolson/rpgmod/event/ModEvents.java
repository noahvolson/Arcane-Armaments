package net.noahvolson.rpgmod.event;

import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.particle.ModParticles;
import net.noahvolson.rpgmod.particle.custom.FireBoltParticles;

@Mod.EventBusSubscriber(modid = RpgMod.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.register(ModParticles.FIREBOLT_PARTICLES.get(), FireBoltParticles.Provider::new);
    }
}
