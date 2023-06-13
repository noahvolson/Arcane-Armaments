package net.noahvolson.rpgmod.entity.spell;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;


public abstract class AbstractMeleeAttack extends AbstractProjectileAbility {
    private double startX = 0;
    private double startY = 0;
    private double startZ = 0;
    private double range = 0;
    public AbstractMeleeAttack(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public AbstractMeleeAttack(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world, SoundEvent hitSound) {
        super(entityType, shooter, world, null, hitSound, null);
        this.setNoGravity(true);

        this.startX = shooter.getX();
        this.startY = shooter.getY();
        this.startZ = shooter.getZ();

        if (shooter instanceof ServerPlayer serverplayer) {
            this.range = serverplayer.getAttackRange();
        }
    }

    public void setRange(double range) {
        this.range = range;
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
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