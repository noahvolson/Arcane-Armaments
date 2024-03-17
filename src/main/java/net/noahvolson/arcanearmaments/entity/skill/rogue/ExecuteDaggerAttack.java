package net.noahvolson.arcanearmaments.entity.skill.rogue;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.noahvolson.arcanearmaments.config.ModDamageSource;
import net.noahvolson.arcanearmaments.entity.skill.AbstractMeleeAttack;
import net.noahvolson.arcanearmaments.entity.skill.AbstractProjectileAbility;
import net.noahvolson.arcanearmaments.entity.skill.SkillType;
import net.noahvolson.arcanearmaments.particle.ModParticles;
import net.noahvolson.arcanearmaments.sound.ModSounds;
import org.jetbrains.annotations.NotNull;

public class ExecuteDaggerAttack extends AbstractMeleeAttack {

    public ExecuteDaggerAttack(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public ExecuteDaggerAttack(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, null);
        this.setDamage(ModDamageSource.EXECUTE, SkillType.EXECUTE.getDamage());
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        Entity entity = ray.getEntity();
        if (entity.level instanceof ServerLevel serverLevel && entity instanceof LivingEntity livingentity) {
            Vec3 eyePos = livingentity.getEyePosition();
            Entity owner = this.getOwner();
            assert owner != null;
            double shiftCloserBy = 0.3;
            double x = eyePos.x() > owner.getX() ? eyePos.x() - shiftCloserBy : eyePos.x() + shiftCloserBy;
            double z = eyePos.z() > owner.getZ() ? eyePos.z() - shiftCloserBy : eyePos.z() + shiftCloserBy;
            if (livingentity.getHealth() / livingentity.getMaxHealth() <= 0.33) {
                livingentity.hurt(ModDamageSource.EXECUTE, livingentity.getHealth() * 100);
                this.level.playSound(null, owner.getX(), owner.getY(), owner.getZ(), ModSounds.EXECUTE_DAGGER.get(), SoundSource.HOSTILE, 0.9F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                serverLevel.sendParticles(ModParticles.EXECUTE_PARTICLES.get(), x, eyePos.y(), z, 1, 0D, 0D,0D, 0D);
            } else {
                this.level.playSound(null, owner.getX(), owner.getY(), owner.getZ(), ModSounds.FAILED_EXECUTE.get(), SoundSource.HOSTILE, 0.9F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                serverLevel.sendParticles(ModParticles.DAGGER_PARTICLES.get(), x, eyePos.y(), z, 1, 0D, 0D,0D, 0D);
            }
        }
    }
}
