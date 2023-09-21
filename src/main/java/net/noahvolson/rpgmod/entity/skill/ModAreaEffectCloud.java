package net.noahvolson.rpgmod.entity.skill;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

public class ModAreaEffectCloud extends AreaEffectCloud {
    private int reapplicationDelay = 20;
    private final Map<Entity, Integer> victims = Maps.newHashMap();
    private final List<MobEffectInstance> effects = Lists.newArrayList();

    public ModAreaEffectCloud(Level p_19707_, double p_19708_, double p_19709_, double p_19710_) {
        super(p_19707_, p_19708_, p_19709_, p_19710_);
    }

    @Override
    public void tick() {
        super.tick();
        boolean flag = this.isWaiting();
        float f = this.getRadius();
        if (this.level.isClientSide) {
            if (flag && this.random.nextBoolean()) {
                return;
            }

            ParticleOptions particleoptions = this.getParticle();
            int i;
            float f1;
            if (flag) {
                i = 2;
                f1 = 0.2F;
            } else {
                i = Mth.ceil((float)Math.PI * f * f);
                f1 = f;
            }

            for(int j = 0; j < i; ++j) {
                float f2 = this.random.nextFloat() * ((float)Math.PI * 2F);
                float f3 = Mth.sqrt(this.random.nextFloat()) * f1;
                double d0 = this.getX() + (double)(Mth.cos(f2) * f3);
                double d2 = this.getY();
                double d4 = this.getZ() + (double)(Mth.sin(f2) * f3);
                double d5;
                double d6;
                double d7;
                if (particleoptions.getType() != ParticleTypes.ENTITY_EFFECT) {
                    if (flag) {
                        d5 = 0.0D;
                        d6 = 0.0D;
                        d7 = 0.0D;
                    } else {
                        d5 = (0.5D - this.random.nextDouble()) * 0.15D;
                        d6 = (double)0.01F;
                        d7 = (0.5D - this.random.nextDouble()) * 0.15D;
                    }
                } else {
                    int k = flag && this.random.nextBoolean() ? 16777215 : this.getColor();
                    d5 = (double)((float)(k >> 16 & 255) / 255.0F);
                    d6 = (double)((float)(k >> 8 & 255) / 255.0F);
                    d7 = (double)((float)(k & 255) / 255.0F);
                }

                this.level.addAlwaysVisibleParticle(particleoptions, d0, d2, d4, d5, d6, d7);
            }
        } else {
            if (this.tickCount >= this.getWaitTime() + this.getDuration()) {
                this.discard();
                return;
            }

            boolean flag1 = this.tickCount < this.getWaitTime();
            if (flag != flag1) {
                this.setWaiting(flag1);
            }

            if (flag1) {
                return;
            }

            if (this.getRadiusPerTick() != 0.0F) {
                f += this.getRadiusPerTick();
                if (f < 0.5F) {
                    this.discard();
                    return;
                }

                this.setRadius(f);
            }

            if (this.tickCount % 5 == 0) {
                this.victims.entrySet().removeIf((p_146784_) -> {
                    return this.tickCount >= p_146784_.getValue();
                });
                List<MobEffectInstance> list = Lists.newArrayList();

                for(MobEffectInstance mobeffectinstance : this.getPotion().getEffects()) {
                    list.add(new MobEffectInstance(mobeffectinstance.getEffect(), mobeffectinstance.getDuration() / 4, mobeffectinstance.getAmplifier(), mobeffectinstance.isAmbient(), mobeffectinstance.isVisible()));
                }

                list.addAll(this.effects);
                if (list.isEmpty()) {
                    this.victims.clear();
                } else {
                    List<LivingEntity> list1 = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
                    if (!list1.isEmpty()) {
                        for(LivingEntity livingentity : list1) {
                            if (!this.victims.containsKey(livingentity) && livingentity.isAffectedByPotions() && livingentity != this.getOwner()) {
                                double d8 = livingentity.getX() - this.getX();
                                double d1 = livingentity.getZ() - this.getZ();
                                double d3 = d8 * d8 + d1 * d1;
                                if (d3 <= (double)(f * f)) {
                                    this.victims.put(livingentity, this.tickCount + this.reapplicationDelay);

                                    for(MobEffectInstance mobeffectinstance1 : list) {
                                        if (mobeffectinstance1.getEffect().isInstantenous()) {
                                            mobeffectinstance1.getEffect().applyInstantenousEffect(this, this.getOwner(), livingentity, mobeffectinstance1.getAmplifier(), 0.5D);
                                        } else {
                                            livingentity.addEffect(new MobEffectInstance(mobeffectinstance1), this);
                                        }
                                    }

                                    if (this.getRadiusOnUse() != 0.0F) {
                                        f += this.getRadiusOnUse();
                                        if (f < 0.5F) {
                                            this.discard();
                                            return;
                                        }

                                        this.setRadius(f);
                                    }

                                    if (this.getDurationOnUse() != 0) {
                                        int duration = this.getDuration() + this.getDurationOnUse();
                                        this.setDuration(duration);
                                        if (this.getDuration() <= 0) {
                                            this.discard();
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public void addEffect(MobEffectInstance p_19717_) {
        this.effects.add(p_19717_);
    }
}
