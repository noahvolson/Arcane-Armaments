package net.noahvolson.rpgmod.entity.skill.warrior;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.noahvolson.rpgmod.entity.skill.AbstractMeleeAttack;
import net.noahvolson.rpgmod.entity.skill.AbstractProjectileAbility;
import net.noahvolson.rpgmod.entity.skill.Skill;

public class BerserkAttack extends AbstractMeleeAttack implements Skill {
    public BerserkAttack(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public BerserkAttack(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, SoundEvents.PLAYER_ATTACK_SWEEP);
        AttributeModifier attr = shooter.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).iterator().next();
        this.setBaseDamage(attr.getAmount() / 2.5); // Need to divide by 2.5 to account for arrow velocity (which multiplies damage)
    }
}
