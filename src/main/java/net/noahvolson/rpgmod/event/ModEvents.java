package net.noahvolson.rpgmod.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraftforge.fml.common.Mod;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.networking.ModMessages;
import net.noahvolson.rpgmod.networking.packet.RpgClassSyncS2CPacket;
import net.noahvolson.rpgmod.player.PlayerRpgClass;
import net.noahvolson.rpgmod.player.PlayerRpgClassProvider;
import net.noahvolson.rpgmod.rpgclass.RpgClass;

import java.util.Objects;

import static net.noahvolson.rpgmod.rpgclass.RpgClasses.*;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = RpgMod.MOD_ID)
    public static class ForgeEvents {

        private static void setPlayerRpgClassCapability(ServerPlayer player, RpgClass rpgClass) {
            player.getCapability(PlayerRpgClassProvider.PLAYER_RPG_CLASS).ifPresent(curClass -> {
                if (!curClass.getRpgClass().equals(rpgClass.getId())) {
                    player.sendSystemMessage(Component.literal("Swapping to " + rpgClass.getId()).withStyle(ChatFormatting.AQUA));
                    curClass.setRpgClass(rpgClass.getId());
                    ModMessages.sendToPlayer(new RpgClassSyncS2CPacket(curClass.getRpgClass()), player);
                }
            });
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.player instanceof ServerPlayer player) {
                ItemStack offhand = player.getOffhandItem();
                if (offhand.is(MAGE.getClassItem())) {
                    setPlayerRpgClassCapability(player, MAGE);
                } else if (offhand.is(ROGUE.getClassItem())) {
                    setPlayerRpgClassCapability(player, ROGUE);
                } else if (offhand.is(WARRIOR.getClassItem())) {
                    setPlayerRpgClassCapability(player, WARRIOR);
                }
            }
        }

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