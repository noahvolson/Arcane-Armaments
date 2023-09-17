package net.noahvolson.rpgmod.entity.skill.warrior;

import com.google.common.collect.Multimap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.noahvolson.rpgmod.entity.skill.AbstractMeleeAttack;
import net.noahvolson.rpgmod.entity.skill.AbstractProjectileAbility;
import net.noahvolson.rpgmod.entity.skill.Skill;

import java.util.Collection;

public class BerserkAttack extends AbstractMeleeAttack implements Skill {
    public BerserkAttack(EntityType<AbstractProjectileAbility> entityType, Level world) {
        super(entityType, world);
    }

    public BerserkAttack(EntityType<AbstractProjectileAbility> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world, SoundEvents.PLAYER_ATTACK_SWEEP);

        Multimap<Attribute, AttributeModifier> mainHand = shooter.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND);
        double damage = mainHand.size() > 0 ? (mainHand.get(Attributes.ATTACK_DAMAGE).iterator().next().getAmount() / 2.5) : 1;
        this.setBaseDamage(damage); // Need to divide by 2.5 to account for arrow velocity (which multiplies damage)
    }
}
