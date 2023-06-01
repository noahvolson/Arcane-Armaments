package net.noahvolson.rpgmod.entity.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IceBoltSpell extends AbstractProjectileSpell {
    private LivingEntity shooter;

    public IceBoltSpell(EntityType<AbstractProjectileSpell> entityType, Level world) {
        super(entityType, world);
    }

    public IceBoltSpell(EntityType<AbstractProjectileSpell> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, SoundEvents.ILLUSIONER_CAST_SPELL, SoundEvents.GLASS_BREAK, SoundEvents.GLASS_BREAK);
        this.shooter = shooter;
        this.setKnockback(0);
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        if (!this.level.isClientSide && ray.getEntity() instanceof LivingEntity livingentity) {
            livingentity.addEffect(new MobEffectInstance(ModEffects.FREEZE.get(), 100, -1));

            BlockPos blockPosAbove = ray.getEntity().getOnPos().above(4);
            BlockPos blockPosFeet = ray.getEntity().getOnPos().above();
            Entity owner = this.getOwner();

            AreaEffectCloud snowCloud = new AreaEffectCloud(this.level, blockPosAbove.getX(),blockPosAbove.getY(), blockPosAbove.getZ());
            snowCloud.setParticle(ModParticles.FREEZE_PARTICLES.get());
            snowCloud.setRadius(2F);
            snowCloud.setDuration(200);
            snowCloud.setWaitTime(0);
            this.level.addFreshEntity(snowCloud);

            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, blockPosFeet.getX(), blockPosFeet.getY(), blockPosFeet.getZ());
            if (owner instanceof LivingEntity) {
                areaeffectcloud.setOwner((LivingEntity)owner);
            }
            areaeffectcloud.setParticle(ModParticles.HIDDEN_PARTICLES.get());
            areaeffectcloud.setRadius(2F);
            areaeffectcloud.setDuration(200);
            areaeffectcloud.setWaitTime(0);
            areaeffectcloud.addEffect(new MobEffectInstance(ModEffects.FREEZE.get(), 30, -1));
            this.level.addFreshEntity(areaeffectcloud);
        }
    }

    @Override
    protected void doEffectsBlock(@NotNull BlockHitResult ray) {
        if (!this.level.isClientSide) {
            BlockPos blockPosAbove = ray.getBlockPos().above(4);
            BlockPos blockPosFeet = ray.getBlockPos().above();
            Entity owner = this.getOwner();

            AreaEffectCloud snowCloud = new AreaEffectCloud(this.level, blockPosAbove.getX(),blockPosAbove.getY(), blockPosAbove.getZ());
            snowCloud.setParticle(ModParticles.FREEZE_PARTICLES.get());
            snowCloud.setRadius(2F);
            snowCloud.setDuration(200);
            snowCloud.setWaitTime(0);
            this.level.addFreshEntity(snowCloud);

            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, blockPosFeet.getX(), blockPosFeet.getY(), blockPosFeet.getZ());
            if (owner instanceof LivingEntity) {
                areaeffectcloud.setOwner((LivingEntity)owner);
            }
            areaeffectcloud.setParticle(ModParticles.HIDDEN_PARTICLES.get());
            areaeffectcloud.setRadius(2F);
            areaeffectcloud.setDuration(200);
            areaeffectcloud.setWaitTime(0);
            areaeffectcloud.addEffect(new MobEffectInstance(ModEffects.FREEZE.get(), 30, -1));
            this.level.addFreshEntity(areaeffectcloud);
        }

    };

    @Override
    protected void tickDespawn() {
        if (this.inGroundTime > 120) {
            this.discard();
        }
    }

    @Override
    protected void makeParticle() {
        if (!this.inGround) {
            for(int j = 0; j < 5; ++j) {

                double magnitude = .02;
                double xD = (2*Math.random() - 1) * magnitude;
                double yD = (2*Math.random() - 1) * magnitude;
                double zD = (2*Math.random() - 1) * magnitude;

                this.level.addParticle(ModParticles.ICEBOLT_PARTICLES.get(), this.getX(), this.getY(), this.getZ(), xD, yD, zD);
                this.level.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        } else {

        }
    }

}