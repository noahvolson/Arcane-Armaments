package net.noahvolson.rpgmod.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.player.PlayerRpgClass;
import net.noahvolson.rpgmod.player.PlayerRpgClassProvider;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = RpgMod.MOD_ID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onRenderGuiOverlayEvent(RenderGuiOverlayEvent.Pre event) {
            if (event.getOverlay() == VanillaGuiOverlay.ARMOR_LEVEL.type()) {
                event.setCanceled(true);
            }
        }

        // Hide armor & drawn weapons
        @SubscribeEvent
        public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
            if (event.getEntity().hasEffect(MobEffects.INVISIBILITY)) {
                event.setCanceled(true);
            }
        }

        // Break invisibility on attack
        @SubscribeEvent
        public static void onAttackEntity(AttackEntityEvent event) {
            Player player = event.getEntity();
            if (player.hasEffect(MobEffects.INVISIBILITY)) {
                player.removeEffect(MobEffects.INVISIBILITY);
            }
        }

        // Take double damage when berserking
        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (event.getEntity() instanceof Player player && player.hasEffect(ModEffects.BERSERK.get())) {
                event.setAmount(event.getAmount() * 2);
            }
        }


        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            if(event.getObject() instanceof Player) {
                if(!event.getObject().getCapability(PlayerRpgClassProvider.PLAYER_RPG_CLASS).isPresent()) {
                    event.addCapability(new ResourceLocation(RpgMod.MOD_ID, "properties"), new PlayerRpgClassProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if(event.isWasDeath()) {
                event.getOriginal().getCapability(PlayerRpgClassProvider.PLAYER_RPG_CLASS).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(PlayerRpgClassProvider.PLAYER_RPG_CLASS).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(PlayerRpgClass.class);
        }

        /*
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            // Maybe move cooldowns in here?
        }
        */
    }
}