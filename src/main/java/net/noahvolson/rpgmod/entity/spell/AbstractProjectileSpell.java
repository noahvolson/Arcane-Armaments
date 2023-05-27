package net.noahvolson.rpgmod.entity.spell;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class AbstractProjectileSpell extends AbstractArrow {

    private SoundEvent castSound;
    private SoundEvent hitEntitySound;
    private SoundEvent hitBlockSound;

    // For registering in ModEntityTypes
    public AbstractProjectileSpell(EntityType<FireBoltEntity> entityType, Level world) {
        super(entityType, world);
    }

    // For ability casts
    public AbstractProjectileSpell(EntityType<FireBoltEntity> entityType, LivingEntity shooter, Level world,
                                   SoundEvent castSound, SoundEvent hitEntitySound, SoundEvent hitBlockSound) {

        super(entityType, shooter, world);
        this.castSound = castSound;
        this.hitEntitySound = hitEntitySound;
        this.hitBlockSound = hitBlockSound;
    }

    // For ability casts
    public AbstractProjectileSpell(EntityType<FireBoltEntity> entityType, LivingEntity shooter, Level world, SoundEvent castSound) {

        super(entityType, shooter, world);
        this.castSound = castSound;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult ray) {

        // Override arrow sound
        this.setSoundEvent(Objects.requireNonNullElseGet(this.hitEntitySound, () -> new SoundEvent(new ResourceLocation("none"))));
        super.onHitEntity(ray);

        // Remove arrows that may have been added by the hit
        Entity target = ray.getEntity();
        if (target instanceof LivingEntity) {
            if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
                LivingEntity livingentity = (LivingEntity)target;
                livingentity.setArrowCount(livingentity.getArrowCount() - 1);
            }
        }
        doEffectsEntity(ray);
    }

    // Override this to do fun things on entity hit
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {};

    @Override
    protected void onHitBlock(@NotNull BlockHitResult ray) {
        this.setSoundEvent(Objects.requireNonNullElseGet(this.hitBlockSound, () -> new SoundEvent(new ResourceLocation("none"))));
        super.onHitBlock(ray);
        doEffectsBlock(ray);
    }

    // Override this to do fun things on block hit
    protected void doEffectsBlock(@NotNull BlockHitResult ray) {};

    // Returns the item stack to give the player
    @Override
    protected @NotNull ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    // returns a packet to sync the entity from the server side to the client side
    // called automatically whenever the arrow is added to the world
    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        this.playSound(castSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            this.makeParticle();
        }
    }

    // Override this to customize spell effects
    protected void makeParticle() {
        for(int j = 0; j < 5; ++j) {
            this.level.addParticle(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
    }

}