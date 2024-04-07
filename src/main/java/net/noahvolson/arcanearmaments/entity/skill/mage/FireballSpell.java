package net.noahvolson.arcanearmaments.entity.skill.mage;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.noahvolson.arcanearmaments.damage.ModDamageSources;
import net.noahvolson.arcanearmaments.entity.skill.AbstractProjectileAbility;
import net.noahvolson.arcanearmaments.entity.skill.SkillType;
import net.noahvolson.arcanearmaments.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

public class FireballSpell extends AbstractProjectileAbility {
    public FireballSpell(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public FireballSpell(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, SoundEvents.GHAST_SHOOT, null, null);
        this.setDamage(new ModDamageSources(this.level.registryAccess()).fireball(), SkillType.FIREBALL.getDamage());
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {

        Entity owner = this.getOwner();
        if (owner != null && !this.level.isClientSide && ray.getEntity() instanceof LivingEntity target) {
            target.knockback(0.7D, owner.getX() - target.getX(), owner.getZ() - target.getZ());
            this.level.explode(this, this.getX(), this.getY() - 0.5, this.getZ(), 1.5f, true, Level.ExplosionInteraction.NONE);
            ray.getEntity().setSecondsOnFire(SkillType.FIREBALL.getDuration() / 20);
        }
    }

    @Override
    protected void doEffectsBlock(@NotNull BlockHitResult ray) {
        if (!this.level.isClientSide) {
            this.level.explode(this, this.getX(), this.getY() - 0.5, this.getZ(), 1.5f, true, Level.ExplosionInteraction.NONE);
        }
    };
    
    @Override
    protected void makeTrailParticle() {
        for(int j = 0; j < 5; ++j) {

            double magnitude = .03;
            double xD = (2*Math.random() - 1) * magnitude;
            double yD = (2*Math.random() - 1) * magnitude;
            double zD = (2*Math.random() - 1) * magnitude;

            this.level.addParticle(ModParticles.FIREBOLT_PARTICLES.get(), this.getX(), this.getY(), this.getZ(), xD, yD, zD);
            this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
    }

}