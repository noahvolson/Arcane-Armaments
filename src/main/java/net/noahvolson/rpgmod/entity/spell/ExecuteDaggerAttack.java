package net.noahvolson.rpgmod.entity.spell;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
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

import java.util.Objects;

public class ExecuteDaggerAttack extends AbstractMeleeAttack{
    private final int DURATION = 100;

    public ExecuteDaggerAttack(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public ExecuteDaggerAttack(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, ModSounds.EXECUTE_DAGGER.get());
        this.setBaseDamage(1);
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        Entity entity = ray.getEntity();
        if (entity.level instanceof ServerLevel serverLevel && entity instanceof LivingEntity livingentity) {
            Vec3 eyePos = livingentity.getEyePosition();
            if (livingentity.getHealth() / livingentity.getMaxHealth() <= 0.33) {
                livingentity.hurt(new DamageSource("execute"), livingentity.getHealth() * 100);
                serverLevel.sendParticles(ModParticles.EXECUTE_PARTICLES.get(), eyePos.x(), eyePos.y(), eyePos.z(), 1, 0D, 0D,0D, 0D);
            } else {
                serverLevel.sendParticles(ModParticles.DAGGER_PARTICLES.get(), eyePos.x(), eyePos.y(), eyePos.z(), 1, 0D, 0D,0D, 0D);
            }
        }
    };
}
