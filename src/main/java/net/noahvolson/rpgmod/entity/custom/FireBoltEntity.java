package net.noahvolson.rpgmod.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.network.NetworkHooks;
import net.noahvolson.rpgmod.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

public class FireBoltEntity extends AbstractArrow {
    public FireBoltEntity(EntityType<FireBoltEntity> entityType, Level world) {
        super(entityType, world);
    }

    public FireBoltEntity(EntityType<FireBoltEntity> entityType, double x, double y, double z, Level world) {
        super(entityType, x, y, z, world);
    }

    public FireBoltEntity(EntityType<FireBoltEntity> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult ray) {
        super.onHitEntity(ray);
        // this, x, y, z, explosionStrength, setsFires, breakMode
        this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.BlockInteraction.NONE);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult ray) {
        super.onHitBlock(ray);
        // To explode on hit:
        // this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.BlockInteraction.NONE);
    }

    // To blow up after 3 seconds
    @Override
    protected void tickDespawn() {
        if (this.inGroundTime > 60){
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.BlockInteraction.NONE);
            this.discard();
        }
    }

    // Returns the item stack to give the player
    // Don't want to return a spell to inventory!
    @Override
    protected @NotNull ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    // returns a packet to sync the entity from the server side to the client side
    // called automatically whenever the arrow is added to the world
    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            this.makeParticle();
        }
    }

    private void makeParticle() {
        for(int j = 0; j < 2; ++j) {
            //this.level.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            //this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D)\
            this.level.addParticle(ModParticles.FIREBOLT_PARTICLES.get(), this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0, 0, 0);
        }
    }

}