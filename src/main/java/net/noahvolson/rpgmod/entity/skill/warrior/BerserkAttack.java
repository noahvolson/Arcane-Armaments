package net.noahvolson.rpgmod.entity.skill.warrior;

import com.google.common.collect.Multimap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.noahvolson.rpgmod.config.ModDamageSource;
import net.noahvolson.rpgmod.entity.skill.AbstractMeleeAttack;
import net.noahvolson.rpgmod.entity.skill.AbstractProjectileAbility;
import net.noahvolson.rpgmod.entity.skill.Skill;
import net.noahvolson.rpgmod.entity.skill.SkillType;
import org.jetbrains.annotations.NotNull;

public class BerserkAttack extends AbstractMeleeAttack implements Skill {
    public BerserkAttack(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public BerserkAttack(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, SoundEvents.PLAYER_ATTACK_SWEEP);
        this.setDamage(ModDamageSource.DECAPITATE, SkillType.BERSERK.getDamage());
    }

    protected void doEffectsEntity(@NotNull EntityHitResult ray) {
        if (ray.getEntity() instanceof ServerPlayer player && player.canDisableShield()) {
            player.disableShield(true);
        }
    }
}
