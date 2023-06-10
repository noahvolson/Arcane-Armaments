package net.noahvolson.rpgmod.entity.spell;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.noahvolson.rpgmod.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DaggerAttack extends AbstractProjectileSpell {
    private int tickCounter = 0;
    private double startX = 0;
    private double startY = 0;
    private double startZ = 0;
    private double range = 0;
    public DaggerAttack(EntityType<AbstractProjectileSpell> entityType, Level world) {
        super(entityType, world);
    }

    public DaggerAttack(EntityType<AbstractProjectileSpell> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, null, SoundEvents.PLAYER_ATTACK_SWEEP, null);
        this.setNoGravity(true);

        this.startX = shooter.getX();
        this.startY = shooter.getY();
        this.startZ = shooter.getZ();

        if (shooter instanceof ServerPlayer serverplayer) {
            this.range = serverplayer.getAttackRange();
        }
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        // disable shield ?
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide) {
            double distance = Math.sqrt(Math.pow(this.getX() - startX, 2) + Math.pow(this.getY() - startY, 2) + Math.pow(this.getZ() - startZ, 2));
            if (!this.isRemoved() && Math.ceil(distance) >= this.range) {
                this.discard();
            }
            tickCounter++;
        }
        super.tick();
    }

}