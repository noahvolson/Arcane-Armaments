package net.noahvolson.rpgmod.event;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.entity.skill.ModAreaEffectCloud;
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

        private static void setPlayerRpgClassCapabilityTick(ServerPlayer player, RpgClass rpgClass) {
            if (player != null) {
                player.getCapability(PlayerRpgClassProvider.PLAYER_RPG_CLASS).ifPresent(curClass -> {
                    if (!curClass.getRpgClass().equals(rpgClass.getId())) {
                        player.sendSystemMessage(Component.literal("Swapping to " + rpgClass.getId()).withStyle(ChatFormatting.AQUA));
                        curClass.setRpgClass(rpgClass.getId());
                        ModMessages.sendToPlayer(new RpgClassSyncS2CPacket(rpgClass.getId()), player);
                    }
                });
            }
        }

        private static void setPlayerRpgClassCapabilityJoin(ServerPlayer player, RpgClass rpgClass) {
            if (player != null) {
                player.getCapability(PlayerRpgClassProvider.PLAYER_RPG_CLASS).ifPresent(curClass -> {
                    curClass.setRpgClass(rpgClass.getId());
                    ModMessages.sendToPlayer(new RpgClassSyncS2CPacket(rpgClass.getId()), player);
                });
            }
        }

        @SubscribeEvent
        public static void onPlayerJoin(EntityJoinLevelEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getAttribute(Attributes.MOVEMENT_SPEED);

                ItemStack offhand = player.getOffhandItem();
                if (offhand.is(MAGE.getClassItem())) {
                    setPlayerRpgClassCapabilityJoin(player, MAGE);
                } else if (offhand.is(ROGUE.getClassItem())) {
                    setPlayerRpgClassCapabilityJoin(player, ROGUE);
                } else if (offhand.is(WARRIOR.getClassItem())) {
                    setPlayerRpgClassCapabilityJoin(player, WARRIOR);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.player instanceof ServerPlayer player) {

                if (player.hasEffect(ModEffects.STOMPING.get()) && player.fallDistance == 0) {
                    player.removeEffect(ModEffects.STOMPING.get());

                    int i = Mth.floor(player.getX());
                    int j = Mth.floor(player.getY() - (double)0.2F);
                    int k = Mth.floor(player.getZ());
                    BlockPos blockpos = new BlockPos(i, j, k);
                    BlockState blockstate = player.level.getBlockState(blockpos);

                    if (blockstate.isAir()) {
                        j = Mth.floor(player.getY() - (double)1.2F);
                        blockpos = new BlockPos(i, j, k);
                        blockstate = player.level.getBlockState(blockpos);
                    }

                    int rumbleRadius = 4;
                    int rumbleDuration = 10;

                    ModAreaEffectCloud rumbleCloud = new ModAreaEffectCloud(player.level, player.getX(), player.getY(), player.getZ());
                    rumbleCloud.setParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(blockpos));
                    rumbleCloud.setRadiusOnUse(0F);
                    rumbleCloud.setRadiusPerTick((float) rumbleRadius / rumbleDuration);
                    rumbleCloud.setDuration(rumbleDuration);
                    rumbleCloud.setWaitTime(0);
                    rumbleCloud.setOwner(player);
                    rumbleCloud.addEffect(new MobEffectInstance(ModEffects.FEAR.get(), 10, 0, false, false, true));
                    player.level.addFreshEntity(rumbleCloud);
                }

                ItemStack offhand = player.getOffhandItem();
                if (offhand.is(MAGE.getClassItem())) {
                    setPlayerRpgClassCapabilityTick(player, MAGE);
                } else if (offhand.is(ROGUE.getClassItem())) {
                    setPlayerRpgClassCapabilityTick(player, ROGUE);
                } else if (offhand.is(WARRIOR.getClassItem())) {
                    setPlayerRpgClassCapabilityTick(player, WARRIOR);
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
            if (event.getEntity() instanceof Player player && player.hasEffect(ModEffects.SHELL.get())) {
                event.setAmount(event.getAmount() / 4);
            }
            if (event.getEntity() instanceof Player player && player.hasEffect(ModEffects.STOMPING.get()) && event.getSource() == DamageSource.FALL) {
                event.setCanceled(true);
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

    }
}