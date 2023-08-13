package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractProjectileAbility extends AbstractArrow implements Skill {

    private SoundEvent castSound;
    private SoundEvent hitEntitySound;
    private SoundEvent hitBlockSound;

    // For registering in ModEntityTypes
    public AbstractProjectileAbility(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    // For ability casts
    public AbstractProjectileAbility(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world,
                                     SoundEvent castSound, SoundEvent hitEntitySound, SoundEvent hitBlockSound) {

        super(entityType, shooter, world);
        this.castSound = castSound;
        this.hitEntitySound = hitEntitySound;
        this.hitBlockSound = hitBlockSound;
    }

    // For ability casts
    public AbstractProjectileAbility(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world, SoundEvent castSound) {

        super(entityType, shooter, world);
        this.castSound = castSound;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult ray) {

        // Override arrow sound
        this.setSoundEvent(this.hitEntitySound);
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
        this.setSoundEvent(this.hitBlockSound);
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
            this.makeTrailParticle();
        }
    }

    @Override
    protected void tickDespawn() {
        this.discard();
    }

    // Override this to customize spell effects
    protected void makeTrailParticle() {
        for(int j = 0; j < 5; ++j) {
            this.level.addParticle(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
    }

    @Override
    public void use(ServerPlayer player) {
        double speed = 3D;
        Vec3 look = player.getLookAngle();
        this.setDeltaMovement(look.x * speed, look.y * speed, look.z * speed);
        player.level.addFreshEntity(this);
    }

    @Override
    public void useTurnover(ServerPlayer player) {

    }

    @Override
    public boolean canUseTurnover(ServerPlayer player) {
        return false;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public int getTurnoverCooldown() {
        return 0;
    }

    @Override
    public boolean isInvisibleCausing() {
        return false;
    }

}