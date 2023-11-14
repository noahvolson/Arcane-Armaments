package net.noahvolson.rpgmod.event;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noahvolson.rpgmod.RpgMod;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.entity.skill.ModAreaEffectCloud;
import net.noahvolson.rpgmod.networking.ModMessages;
import net.noahvolson.rpgmod.networking.packet.RpgClassSyncS2CPacket;
import net.noahvolson.rpgmod.particle.ModParticles;
import net.noahvolson.rpgmod.player.PlayerRpgClass;
import net.noahvolson.rpgmod.player.PlayerRpgClassProvider;
import net.noahvolson.rpgmod.rpgclass.RpgClass;
import net.noahvolson.rpgmod.sound.ModSounds;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.noahvolson.rpgmod.rpgclass.RpgClasses.*;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = RpgMod.MOD_ID)
    public static class ForgeEvents {
        private static final int RUMBLE_RADIUS = 4;
        private static final int RUMBLE_DURATION = 10;

        static ArrayList<UUID> fallDamageImmune = new ArrayList<>();

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
                } else if (offhand.is(CLERIC.getClassItem())) {
                    setPlayerRpgClassCapabilityJoin(player, CLERIC);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.player instanceof ServerPlayer player) {
                fallDamageImmune.remove(player.getUUID());

                if (player.hasEffect(ModEffects.STOMPING.get()) && (player.isOnGround() || player.isInWater())) {
                    player.removeEffect(ModEffects.STOMPING.get());
                    fallDamageImmune.add(player.getUUID());

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

                    ModAreaEffectCloud rumbleCloud = new ModAreaEffectCloud(player.level, player.getX(), player.getY(), player.getZ());
                    rumbleCloud.setParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(blockpos));
                    rumbleCloud.setRadiusOnUse(0F);
                    rumbleCloud.setRadiusPerTick((float) RUMBLE_RADIUS / RUMBLE_DURATION);
                    rumbleCloud.setDuration(RUMBLE_DURATION);
                    rumbleCloud.setWaitTime(0);
                    rumbleCloud.setOwner(player);
                    rumbleCloud.addEffect(new MobEffectInstance(ModEffects.STUNNED.get(), 20, 0, false, false, true));
                    player.level.addFreshEntity(rumbleCloud);

                    ModAreaEffectCloud upperRumbleCloud = new ModAreaEffectCloud(player.level, player.getX(), player.getY() + 1, player.getZ());
                    upperRumbleCloud.setParticle(ModParticles.HIDDEN_PARTICLES.get());
                    upperRumbleCloud.setRadiusOnUse(0F);
                    upperRumbleCloud.setRadiusPerTick((float) RUMBLE_RADIUS / RUMBLE_DURATION);
                    upperRumbleCloud.setDuration(RUMBLE_DURATION);
                    upperRumbleCloud.setWaitTime(0);
                    upperRumbleCloud.setOwner(player);
                    upperRumbleCloud.addEffect(new MobEffectInstance(ModEffects.STUNNED.get(), 20, 0, false, false, true));
                    player.level.addFreshEntity(upperRumbleCloud);

                    if (player.isInWater()) {
                        player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.HOSTILE, 1F, 1.2F / (player.level.random.nextFloat() * 0.2F + 0.9F));
                    }
                    else {
                        player.level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.STOMP_IMPACT.get(), SoundSource.HOSTILE, 1F, 1.2F / (player.level.random.nextFloat() * 0.2F + 0.9F));
                    }
                }

                ItemStack offhand = player.getOffhandItem();
                if (offhand.is(MAGE.getClassItem())) {
                    setPlayerRpgClassCapabilityTick(player, MAGE);
                } else if (offhand.is(ROGUE.getClassItem())) {
                    setPlayerRpgClassCapabilityTick(player, ROGUE);
                } else if (offhand.is(WARRIOR.getClassItem())) {
                    setPlayerRpgClassCapabilityTick(player, WARRIOR);
                } else if (offhand.is(CLERIC.getClassItem())) {
                    setPlayerRpgClassCapabilityTick(player, CLERIC);
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

        // Remove mob aggro on invisibility and blindness
        @SubscribeEvent
        public static void onLivingSetAttackTargetEvent (LivingSetAttackTargetEvent event) {
            if (event.getTarget() instanceof Player player) {
                if (event.getEntity() instanceof Mob mob) {
                    if (player.hasEffect(MobEffects.INVISIBILITY)) {
                        mob.setTarget(null);
                    } else if (mob.hasEffect(MobEffects.BLINDNESS)) {
                        mob.setTarget(null);
                    }
                }
            }
        }

        // Break invisibility on attack
        @SubscribeEvent
        public static void onAttackEntity(AttackEntityEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                if (player.hasEffect(ModEffects.BLESSED_BLADE.get()) && event.getTarget() instanceof LivingEntity target) {

                    target.setHealth(target.getHealth() - 1);
                    player.heal(1);

                    AreaEffectCloud sparkleCloud = new AreaEffectCloud(target.level, target.getX(), target.getY() + 1, target.getZ());
                    sparkleCloud.setParticle(ModParticles.BLESSED_BLADE_PARTICLES.get());
                    sparkleCloud.setRadius(1F);
                    sparkleCloud.setDuration(5);
                    sparkleCloud.setWaitTime(0);
                    player.level.addFreshEntity(sparkleCloud);

                    player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ILLUSIONER_MIRROR_MOVE, SoundSource.HOSTILE, 1F, 1.2F / (player.level.random.nextFloat() * 0.2F + 0.9F));
                }

                if (player.hasEffect(MobEffects.INVISIBILITY)) {
                    player.removeEffect(MobEffects.INVISIBILITY);
                }
            }
        }

        private static ArrayList<Vec3> getSpherePoints(int samples, int r) {
            ArrayList<Vec3> points = new ArrayList<>();
            double phi = Math.PI * (Math.sqrt(5.) - 1.);        // golden angle in radians

            for (int i = 0; i < samples; i++) {
                float y = 1 - (i / (float)(samples - 1)) * 2;   // y goes from 1 to -1
                double radius = Math.sqrt(1 - y * y);           // radius at y
                double theta = phi * i;
                double x = Math.cos(theta) * radius;
                double z = Math.sin(theta) * radius;
                points.add(new Vec3(x * r, y * r, z * r));
            }
            return points;
        }

        private static void shieldPlayer (LivingHurtEvent event, ServerPlayer player, ServerLevel level, MobEffect effect, MobEffect newEffect) {
            if (player.hasEffect(effect)) {
                int duration = player.getEffect(effect).getDuration();
                player.removeEffect(effect);
                if (newEffect != null) {
                    player.addEffect(new MobEffectInstance(newEffect, duration, 0, false, false, true));
                }
                ArrayList<Vec3> points = getSpherePoints(500, 1);
                for (Vec3 point : points) {
                    Vec3 shifted = point.add(player.position());
                    level.sendParticles(ModParticles.HOLY_SHIELD_PARTICLES.get(), shifted.x, shifted.y + 1, shifted.z, 1, 0, 0, 0, 0);
                }

                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void onLivingHeal(LivingHealEvent event) {
            LivingEntity entity = event.getEntity();
            if(entity.hasEffect(ModEffects.SMITING.get())) {
                entity.hurt(new DamageSource("smiting"), event.getAmount());
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (event.getEntity() instanceof ServerPlayer player && player.level instanceof ServerLevel level) {

                shieldPlayer(event, player, level, ModEffects.HOLY_SHIELD_1.get(), null);
                shieldPlayer(event, player, level, ModEffects.HOLY_SHIELD_2.get(), ModEffects.HOLY_SHIELD_1.get());
                shieldPlayer(event, player, level, ModEffects.HOLY_SHIELD_3.get(), ModEffects.HOLY_SHIELD_2.get());

                if (player.hasEffect(ModEffects.BERSERK.get())) {
                    event.setAmount(event.getAmount() * 1.5F);
                }
                if (player.hasEffect(ModEffects.SHELL.get())) {
                    event.setAmount(event.getAmount() / 4);
                }
                if (event.getSource() == DamageSource.FALL) {
                    if (player.hasEffect(ModEffects.STOMPING.get()) || fallDamageImmune.contains(player.getUUID())) {
                        fallDamageImmune.remove(player.getUUID());

                        List<LivingEntity> list = player.level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, player, player.getBoundingBox().inflate(RUMBLE_RADIUS, 0, RUMBLE_RADIUS));
                        for (LivingEntity target : list) {
                            target.hurt(new DamageSource("stomp"), event.getAmount());
                        }
                        event.setCanceled(true);
                    }
                }
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