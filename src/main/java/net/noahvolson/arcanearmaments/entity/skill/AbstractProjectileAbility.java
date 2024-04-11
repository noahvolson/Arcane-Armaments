package net.noahvolson.arcanearmaments.entity.skill;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
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
import net.noahvolson.arcanearmaments.damage.ModDamageSources;
import net.noahvolson.arcanearmaments.sound.ModSounds;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractProjectileAbility extends AbstractArrow implements Skill {

    private SoundEvent castSound = ModSounds.SILENT.get();
    private SoundEvent hitEntitySound = ModSounds.SILENT.get();
    private SoundEvent hitBlockSound = ModSounds.SILENT.get();
    private double speed = 3D;
    private int hitDamage = 0;
    private DamageSource damageSource = new DamageSources(this.level().registryAccess()).magic();

    // For registering in ModEntityTypes
    public AbstractProjectileAbility(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    // For ability casts
    public AbstractProjectileAbility(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world,
                                     SoundEvent castSound, SoundEvent hitEntitySound, SoundEvent hitBlockSound) {

        super(entityType, shooter, world);
        this.castSound = castSound != null ? castSound : this.castSound;
        this.hitEntitySound = hitEntitySound != null ? hitEntitySound : this.hitEntitySound;
        this.hitBlockSound = hitBlockSound != null ? hitBlockSound : this.hitBlockSound;

    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult ray) {
        Entity target = ray.getEntity();
        if (target instanceof LivingEntity) {
            Vec3 delta = this.getDeltaMovement();

            // Arrow base damage factors in speed, which makes damage calculations needlessly complex
            if (this.hitDamage > 0) {
                this.setBaseDamage(0);

                // if base damage is 0 soundEvent won't fire, so we need to manually play it
                this.playSound(hitEntitySound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                target.hurt(damageSource, hitDamage);
            }

            // Override arrow sound
            this.setSoundEvent(this.hitEntitySound);

            // Implemented by subclasses
            doEffectsEntity(ray);

            super.onHitEntity(ray);

            // Remove arrows that may have been added by the hit
            if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
                LivingEntity livingentity = (LivingEntity)target;
                livingentity.setArrowCount(livingentity.getArrowCount() - 1);
            }

            // Do this because super() will reflect 0 damage arrows
            if (this.getPierceLevel() > 0) {
                this.setDeltaMovement(delta);
            } else {
                this.discard();
            }
        }
    }

    // Override this to do fun things on entity hit
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {};

    @Override
    protected void onHitBlock(@NotNull BlockHitResult ray) {
        this.setSoundEvent(this.hitBlockSound);
        doEffectsBlock(ray);
        super.onHitBlock(ray);
    }

    // Override this to do fun things on block hit
    protected void doEffectsBlock(@NotNull BlockHitResult ray) {};

    // Returns the item stack to give the player
    @Override
    protected @NotNull ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
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
            this.level().addParticle(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public void use(ServerPlayer player) {
        Vec3 look = player.getLookAngle();
        this.setDeltaMovement(look.x * speed, look.y * speed, look.z * speed);
        player.level().addFreshEntity(this);
        this.playSound(castSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
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
    public boolean isInvisibleCausing() {
        return false;
    }

    public void setDamage(DamageSource damageSource, int hitDamage) {
        this.damageSource = damageSource;
        this.hitDamage = hitDamage;
    }
}