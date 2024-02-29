package net.noahvolson.rpgmod.entity.skill.mage;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.noahvolson.rpgmod.config.ModDamageSource;
import net.noahvolson.rpgmod.entity.skill.AbstractProjectileAbility;
import net.noahvolson.rpgmod.entity.skill.SkillType;
import net.noahvolson.rpgmod.entity.skill.cleric.HealingAuraSkill;
import net.noahvolson.rpgmod.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FireballSpell extends AbstractProjectileAbility {
    public FireballSpell(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public FireballSpell(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, SoundEvents.GHAST_SHOOT);
        this.setDamage(ModDamageSource.FIREBALL, SkillType.FIREBALL.getDamage());
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {

        Entity owner = this.getOwner();
        if (owner != null && !this.level.isClientSide && ray.getEntity() instanceof LivingEntity target) {
            target.knockback(0.7D, owner.getX() - target.getX(), owner.getZ() - target.getZ());
            this.level.explode(this, this.getX(), this.getY() - 0.5, this.getZ(), 1.25f, true, Explosion.BlockInteraction.NONE);
            ray.getEntity().setSecondsOnFire(SkillType.FIREBALL.getDuration() / 20);
        }
    }

    @Override
    protected void doEffectsBlock(@NotNull BlockHitResult ray) {
        if (!this.level.isClientSide) {
            this.level.explode(this, this.getX(), this.getY() - 0.5, this.getZ(), 1.25f, true, Explosion.BlockInteraction.NONE);
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