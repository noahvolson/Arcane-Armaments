package net.noahvolson.rpgmod.entity.spell;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.noahvolson.rpgmod.particle.ModParticles;
import org.jetbrains.annotations.NotNull;

public class FireBoltSpell extends AbstractProjectileSpell {
    public FireBoltSpell(EntityType<AbstractProjectileSpell> entityType, Level world) {
        super(entityType, world);
    }

    public FireBoltSpell(EntityType<AbstractProjectileSpell> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, SoundEvents.GHAST_SHOOT);
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1f, true, Explosion.BlockInteraction.NONE);
        ray.getEntity().setSecondsOnFire(4);
    }

    @Override
    protected void doEffectsBlock(@NotNull BlockHitResult ray) {
        this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1f, true, Explosion.BlockInteraction.NONE);
    };
    
    @Override
    protected void makeParticle() {
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