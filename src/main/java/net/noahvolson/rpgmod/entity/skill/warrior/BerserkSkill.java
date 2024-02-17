package net.noahvolson.rpgmod.entity.skill.warrior;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.noahvolson.rpgmod.effect.ModEffects;
import net.noahvolson.rpgmod.entity.ModEntityTypes;
import net.noahvolson.rpgmod.entity.skill.Skill;
import net.noahvolson.rpgmod.entity.skill.SkillType;
import net.noahvolson.rpgmod.sound.ModSounds;

public class BerserkSkill implements Skill {

    public BerserkSkill(ServerPlayer player) {
    }

    @Override
    public void use(ServerPlayer player) {
        new BerserkAttack(ModEntityTypes.BERSERK.get(), player, player.level).use(player);
        player.addEffect(new MobEffectInstance(ModEffects.BERSERK.get(), SkillType.BERSERK.getDuration(), 0, false, false, true));
        player.level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.BERSERK.get(), SoundSource.HOSTILE, 10F, 1.2F / (player.level.random.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    public void useTurnover(ServerPlayer player) {
        new BerserkAttack(ModEntityTypes.BERSERK.get(), player, player.level).use(player);
    }

    @Override
    public boolean canUseTurnover(ServerPlayer player) {
        return player.hasEffect(ModEffects.BERSERK.get());
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public boolean isInvisibleCausing() {
        return false;
    }

}
