package net.noahvolson.rpgmod.event;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.effect.ModEffects;

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
    }
}