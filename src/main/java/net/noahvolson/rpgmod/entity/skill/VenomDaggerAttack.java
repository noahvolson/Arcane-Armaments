package net.noahvolson.rpgmod.entity.skill;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.particle.ModParticles;
import net.noahvolson.rpgmod.sound.ModSounds;
import org.jetbrains.annotations.NotNull;

public class VenomDaggerAttack extends AbstractMeleeAttack{
    private final int DURATION = 60;

    public VenomDaggerAttack(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
        this.setBaseDamage(1);
    }

    public VenomDaggerAttack(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, ModSounds.VENOM_DAGGER.get());
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        Entity entity = ray.getEntity();
        if (entity.level instanceof ServerLevel serverLevel && entity instanceof LivingEntity livingentity) {
            livingentity.addEffect(new MobEffectInstance(ModEffects.VENOM.get(), DURATION, -1));

            double yShift = 1;
            AreaEffectCloud venomCloud = new AreaEffectCloud(livingentity.level, livingentity.getX(), livingentity.blockPosition().getY() + yShift, livingentity.getZ());
            venomCloud.setParticle(ModParticles.VENOM_PARTICLES.get());
            venomCloud.setRadius(.45F);
            venomCloud.setDuration(5);
            venomCloud.setWaitTime(0);
            this.level.addFreshEntity(venomCloud);

            Vec3 eyePos = livingentity.getEyePosition();
            Entity owner = this.getOwner();
            assert owner != null;
            double shiftCloserBy = 0.3;
            double x = eyePos.x() > owner.getX() ? eyePos.x() - shiftCloserBy : eyePos.x() + shiftCloserBy;
            double z = eyePos.z() > owner.getZ() ? eyePos.z() - shiftCloserBy : eyePos.z() + shiftCloserBy;
            serverLevel.sendParticles(ModParticles.DAGGER_PARTICLES.get(), x, eyePos.y(), z, 1, 0D, 0D,0D, 0D);
        }
    };
}
