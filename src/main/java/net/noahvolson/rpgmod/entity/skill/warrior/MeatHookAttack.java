package net.noahvolson.rpgmod.entity.skill.warrior;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.noahvolson.rpgmod.entity.skill.AbstractProjectileAbility;
import org.jetbrains.annotations.NotNull;


public class MeatHookAttack extends AbstractProjectileAbility {
    public MeatHookAttack(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public MeatHookAttack(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, SoundEvents.METAL_HIT);
        this.setBaseDamage(0);
    }

    @Override
    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        pullEntity(ray.getEntity());
    }

    protected void pullEntity(Entity target) {
        if (target != null && this.getOwner() != null) {
            Vec3 vec3 = (new Vec3(this.getOwner().getX() - target.getX(), this.getOwner().getY() - target.getY(), this.getOwner().getZ() - target.getZ())).scale(0.23D);
            System.out.println("Pulling entity! - Vec3: " + vec3);
            target.setDeltaMovement(target.getDeltaMovement().add(vec3));
        }
    }
}
