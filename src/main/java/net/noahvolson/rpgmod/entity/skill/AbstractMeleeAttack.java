package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;


public abstract class AbstractMeleeAttack extends AbstractProjectileAbility {
    private double startX = 0;
    private double startY = 0;
    private double startZ = 0;
    private double range = 0;

    private SoundEvent attackSound;
    public AbstractMeleeAttack(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public AbstractMeleeAttack(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world, SoundEvent attackSound) {
        super(entityType, shooter, world, null, null, null);
        this.setNoGravity(true);

        this.startX = shooter.getX();
        this.startY = shooter.getY();
        this.startZ = shooter.getZ();
        this.attackSound = attackSound;

        if (shooter instanceof ServerPlayer serverplayer) {
            this.range = serverplayer.getAttackRange();
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
        if (!this.level.isClientSide) {
            this.level.playSound(null, owner.getX(), owner.getY(), owner.getZ(), attackSound, SoundSource.HOSTILE, 1F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        }
        super.onHitEntity(ray);
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide) {
            double distance = Math.sqrt(Math.pow(this.getX() - startX, 2) + Math.pow(this.getY() - startY, 2) + Math.pow(this.getZ() - startZ, 2));
            if (!this.isRemoved() && Math.ceil(distance) >= this.range) {
                this.discard();
            }
        }
        super.tick();
    }

    @Override
    protected void makeTrailParticle() {}
}