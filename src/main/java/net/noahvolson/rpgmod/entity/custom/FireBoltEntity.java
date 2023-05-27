package net.noahvolson.rpgmod.entity.custom;

import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.noahvolson.rpgmod.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

public class FireBoltEntity extends AbstractArrow {
    public FireBoltEntity(EntityType<FireBoltEntity> entityType, Level world) {
        super(entityType, world);
        this.playSound(SoundEvents.GHAST_SHOOT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

    public FireBoltEntity(EntityType<FireBoltEntity> entityType, double x, double y, double z, Level world) {
        super(entityType, x, y, z, world);
        this.playSound(SoundEvents.GHAST_SHOOT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

    public FireBoltEntity(EntityType<FireBoltEntity> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world);
        this.playSound(SoundEvents.GHAST_SHOOT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult ray) {
        this.setSoundEvent(new SoundEvent(new ResourceLocation("none")));
        super.onHitEntity(ray);
        // this, x, y, z, explosionStrength, setsFires, breakMode
        this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1f, true, Explosion.BlockInteraction.NONE);

        Entity target = ray.getEntity();

        // Remove arrows that may have been added by the hit
        if (target instanceof LivingEntity) {
            if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
                LivingEntity livingentity = (LivingEntity)target;
                livingentity.setArrowCount(livingentity.getArrowCount() - 1);
            }
        }

        ray.getEntity().setSecondsOnFire(4);

    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult ray) {
        this.setSoundEvent(new SoundEvent(new ResourceLocation("none")));
        super.onHitBlock(ray);
        // To explode on hit:
        this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1f, true, Explosion.BlockInteraction.NONE);
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
        for(int j = 0; j < 5; ++j) {

            double magnitude = .03;
            double xD = (2*Math.random() - 1) * magnitude;
            double yD = (2*Math.random() - 1) * magnitude;
            double zD = (2*Math.random() - 1) * magnitude;

            this.level.addParticle(ModParticles.FIREBOLT_PARTICLES.get(), this.getX(), this.getY(), this.getZ(), xD, yD, zD);
        }
    }

}