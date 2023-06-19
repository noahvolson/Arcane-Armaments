package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.noahvolson.rpgmod.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

public class BlinkSpell extends AbstractProjectileAbility {
    private int tickCounter = 0;
    public BlinkSpell(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public BlinkSpell(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, SoundEvents.EVOKER_CAST_SPELL, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundEvents.CHORUS_FRUIT_TELEPORT);
        this.setNoGravity(true);
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        if (ray.getEntity() instanceof LivingEntity livingentity) {
            blinkTo(livingentity.getX(), livingentity.getY(), livingentity.getZ());
        }
    }

    @Override
    protected void doEffectsBlock(@NotNull BlockHitResult ray) {
        blinkTo(this.getX(), this.getY(), this.getZ());
        this.discard();
    };

    protected void blinkTo(double x, double y, double z) {
        if (!this.level.isClientSide) {
            Entity entity = this.getOwner();
            if (entity instanceof ServerPlayer serverplayer) {
                serverplayer.teleportTo(x, y, z);
                serverplayer.level.playSound(null, serverplayer.blockPosition(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.HOSTILE, 1F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                serverplayer.resetFallDistance();
            }
        }
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide) {
            if (tickCounter >= 5 && !this.isRemoved()) {
                blinkTo(this.getX(), this.getY(), this.getZ());
                this.discard();
            }
            tickCounter++;
        }
        super.tick();
    }

    @Override
    protected void makeTrailParticle() {
        this.level.addParticle(ModParticles.BLINK_PARTICLES.get(), this.getX(), this.getY(), this.getZ(), 0, 0, 0);
    }

}