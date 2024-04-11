package net.noahvolson.arcanearmaments.entity.skill;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.noahvolson.arcanearmaments.effect.ModEffects;
import net.noahvolson.arcanearmaments.particle.ModParticles;
import net.noahvolson.arcanearmaments.sound.ModSounds;
import org.jetbrains.annotations.NotNull;


public abstract class AbstractMeleeAttack extends AbstractProjectileAbility {
    private double startX = 0;
    private double startY = 0;
    private double startZ = 0;
    private double range = 0;

    private SoundEvent attackSound = ModSounds.SILENT.get();
    public AbstractMeleeAttack(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public AbstractMeleeAttack(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world, SoundEvent attackSound) {
        super(entityType, shooter, world, null, null, null);
        this.setNoGravity(true);

        this.startX = shooter.getX();
        this.startY = shooter.getY();
        this.startZ = shooter.getZ();
        this.attackSound = attackSound != null ? attackSound : this.attackSound;

        if (shooter instanceof ServerPlayer serverplayer) {
            this.range = serverplayer.getEntityReach();
        }
    }
    public void setRange(double range) {
        this.range = range;
    }

    public void setAttackSound(SoundEvent attackSound) {
        this.attackSound = attackSound;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult ray) {
        Entity owner = this.getOwner();
        if (owner != null && !this.level().isClientSide && ray.getEntity() instanceof LivingEntity target) {
            if (owner instanceof LivingEntity livingOwner && livingOwner.hasEffect(ModEffects.BLESSED_BLADE.get())) {
                livingOwner.heal(SkillType.BLESSED_BLADES.getHealing());
                target.setHealth(target.getHealth() - SkillType.BLESSED_BLADES.getDamage());

                AreaEffectCloud sparkleCloud = new AreaEffectCloud(target.level(), target.getX(), target.getY() + 1, target.getZ());
                sparkleCloud.setParticle(ModParticles.BLESSED_BLADE_PARTICLES.get());
                sparkleCloud.setRadius(1F);
                sparkleCloud.setDuration(5);
                sparkleCloud.setWaitTime(0);
                livingOwner.level().addFreshEntity(sparkleCloud);

                livingOwner.level().playSound(null, livingOwner.getX(), livingOwner.getY(), livingOwner.getZ(), SoundEvents.ILLUSIONER_MIRROR_MOVE, SoundSource.HOSTILE, 1F, 1.2F / (livingOwner.level().random.nextFloat() * 0.2F + 0.9F));
            }
            target.knockback(0.5D, owner.getX() - target.getX(), owner.getZ() - target.getZ());
            this.level().playSound(null, owner.getX(), owner.getY(), owner.getZ(), attackSound, SoundSource.HOSTILE, 1F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        }
        super.onHitEntity(ray);
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            double distance = Math.sqrt(Math.pow(this.getX() - startX, 2) + Math.pow(this.getY() - startY, 2) + Math.pow(this.getZ() - startZ, 2));
            if (!this.isRemoved() && Math.ceil(distance) >= this.range) {
                this.discard();
            }
        }
        super.tick();
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getStartZ() {
        return startZ;
    }

    public double getRange() {
        return range;
    }

    @Override
    protected void makeTrailParticle() {}
}