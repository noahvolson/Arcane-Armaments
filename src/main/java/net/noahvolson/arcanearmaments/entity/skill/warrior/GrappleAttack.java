package net.noahvolson.arcanearmaments.entity.skill.warrior;

import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.noahvolson.arcanearmaments.damage.ModDamageSources;
import net.noahvolson.arcanearmaments.entity.skill.AbstractProjectileAbility;
import net.noahvolson.arcanearmaments.entity.skill.SkillType;
import net.noahvolson.arcanearmaments.particle.ModParticles;
import net.noahvolson.arcanearmaments.sound.ModSounds;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class GrappleAttack extends AbstractProjectileAbility implements GeoEntity {
    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
    private int life;
    private Vec3 lastTickPos;

    public GrappleAttack(EntityType<? extends AbstractProjectileAbility> entityType, Level world) {
        super((EntityType<AbstractProjectileAbility>) entityType, world);
    }

    public GrappleAttack(EntityType<? extends AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super((EntityType<AbstractProjectileAbility>) entityType, shooter, world, ModSounds.GRAPPLE_CAST.get(), ModSounds.RUPTURE_DAGGER.get(), ModSounds.GRAPPLE_GROUND.get());
        this.setDamage(new ModDamageSources(this.level().registryAccess()).grapple(), SkillType.GRAPPLE.getDamage());
        this.setSpeed(2D);
        this.life = 0;
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        pullEntity(ray.getEntity());
    }

    protected void pullEntity(Entity target) {
        if (target != null && this.getOwner() != null) {
            Vec3 vec3 = (new Vec3(this.getOwner().getX() - target.getX(), this.getOwner().getY() - target.getY(), this.getOwner().getZ() - target.getZ())).scale(0.23D);
            target.setDeltaMovement(target.getDeltaMovement().add(vec3));
        }
    }

    private PlayState predicate(AnimationState animationState) {
        return PlayState.CONTINUE; // No animations for projectile
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    protected void makeTrailParticle() {
        if (!this.inGround && this.lastTickPos != null) {
            for(int j = 0; j < 5; ++j) {
                this.level().addParticle(ModParticles.CHAIN_PARTICLES.get(), lastTickPos.x, lastTickPos.y, lastTickPos.z, 0, 0, 0);
                this.level().addParticle(ModParticles.SHELL_PARTICLES.get(), lastTickPos.x, lastTickPos.y, lastTickPos.z, 0, 0, 0);
            }
        }
        this.lastTickPos = new Vec3(this.getX(), this.getY(), this.getZ());
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    protected void tickDespawn() {
        ++this.life;
        if (this.life >= 300) {
            this.discard();
        }
    }
}
