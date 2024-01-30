package net.noahvolson.rpgmod.entity.skill.cleric;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.entity.skill.AbstractProjectileAbility;
import net.noahvolson.rpgmod.entity.skill.SkillType;
import net.noahvolson.rpgmod.particle.ModParticles;
import net.noahvolson.rpgmod.sound.ModSounds;
import org.jetbrains.annotations.NotNull;

public class SmitingRaySkill extends AbstractProjectileAbility {
    private final double RANGE = 25;

    private double startX = 0;
    private double startY = 0;
    private double startZ = 0;

    public SmitingRaySkill(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public SmitingRaySkill(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, ModSounds.SMITING_RAY_CAST.get(), ModSounds.SMITING_RAY_IMPACT.get(), ModSounds.SMITING_RAY_IMPACT.get());
        this.setNoGravity(true);

        this.startX = shooter.getX();
        this.startY = shooter.getY();
        this.startZ = shooter.getZ();

        this.setPierceLevel((byte)16);
        this.setSpeed(1.5);

        this.setDamage(new DamageSource("smiting_ray"), SkillType.SMITING_RAY.getDamage());
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        if (!this.level.isClientSide && ray.getEntity() instanceof LivingEntity livingentity) {
            livingentity.addEffect(new MobEffectInstance(ModEffects.SMITING.get(), SkillType.SMITING_RAY.getDuration(), 1));
        }
    }

    @Override
    protected void doEffectsBlock(@NotNull BlockHitResult ray) {
    };

    @Override
    public void tick() {
        if (!this.level.isClientSide) {
            double distance = Math.sqrt(Math.pow(this.getX() - startX, 2) + Math.pow(this.getY() - startY, 2) + Math.pow(this.getZ() - startZ, 2));
            if (!this.isRemoved() && Math.ceil(distance) >= this.RANGE) {
                this.discard();
            }
        }
        super.tick();
    }

    @Override
    protected void makeTrailParticle() {
        for(int j = 0; j < 5; ++j) {

            double magnitude = .025;
            double xD = (2*Math.random() - 1) * magnitude;
            double yD = (2*Math.random() - 1) * magnitude;
            double zD = (2*Math.random() - 1) * magnitude;

            this.level.addParticle(ModParticles.SMITING_RAY_PARTICLES.get(), this.getX(), this.getY(), this.getZ(), xD, yD, zD);
        }
    }

}