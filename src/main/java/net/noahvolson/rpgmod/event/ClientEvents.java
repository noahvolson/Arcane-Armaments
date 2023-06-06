package net.noahvolson.rpgmod.event;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.networking.ModMessages;
import net.noahvolson.rpgmod.networking.packet.AbilityC2SPacket;
import net.noahvolson.rpgmod.particle.ModParticles;
import net.noahvolson.rpgmod.particle.custom.*;
import net.noahvolson.rpgmod.util.KeyBinding;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = RpgMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.ABILITY_1_KEY.consumeClick()) {
                ModMessages.sendToServer(new AbilityC2SPacket(1));
            }
            if(KeyBinding.ABILITY_2_KEY.consumeClick()) {
                ModMessages.sendToServer(new AbilityC2SPacket(2));
            }
            if(KeyBinding.ABILITY_3_KEY.consumeClick()) {
                ModMessages.sendToServer(new AbilityC2SPacket(3));
            }
            if(KeyBinding.ABILITY_4_KEY.consumeClick()) {
                ModMessages.sendToServer(new AbilityC2SPacket(4));
            }
        }
    }

    @Mod.EventBusSubscriber(modid = RpgMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {

            event.register(KeyBinding.ABILITY_1_KEY);
            event.register(KeyBinding.ABILITY_2_KEY);
            event.register(KeyBinding.ABILITY_3_KEY);
            event.register(KeyBinding.ABILITY_4_KEY);
        }

        @SubscribeEvent
        public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
            event.register(ModParticles.HIDDEN_PARTICLES.get(), HiddenParticle.Provider::new);
            event.register(ModParticles.BLINK_PARTICLES.get(), BlinkParticle.Provider::new);
            event.register(ModParticles.FIREBOLT_PARTICLES.get(), FireballParticle.Provider::new);
            event.register(ModParticles.ICEBOLT_PARTICLES.get(), BlizzardParticle.Provider::new);
            event.register(ModParticles.FREEZE_PARTICLES.get(), FreezeParticle.Provider::new);
            event.register(ModParticles.THUNDER_PARTICLES.get(), ThunderParticle.Provider::new);
            event.register(ModParticles.RUNE_PARTICLES.get(), RuneParticle.Provider::new);
            event.register(ModParticles.ZAPPED_PARTICLES.get(), ZappedParticle.Provider::new);
        }
    }
}
