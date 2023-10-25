package net.noahvolson.rpgmod.entity.skill.cleric;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.noahvolson.rpgmod.entity.skill.AbstractProjectileAbility;
import net.noahvolson.rpgmod.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

public class SmitingRaySkill extends AbstractProjectileAbility {
    public SmitingRaySkill(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public SmitingRaySkill(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, null);
        this.setNoGravity(true);
        this.setPierceLevel((byte)16);
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        ray.getEntity().setSecondsOnFire(4);
    }

    @Override
    protected void doEffectsBlock(@NotNull BlockHitResult ray) {
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