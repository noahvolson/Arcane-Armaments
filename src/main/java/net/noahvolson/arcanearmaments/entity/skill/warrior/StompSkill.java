package net.noahvolson.arcanearmaments.entity.skill.warrior;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.noahvolson.arcanearmaments.effect.ModEffects;
import net.noahvolson.arcanearmaments.entity.skill.Skill;

public class StompSkill implements Skill {
    public StompSkill(ServerPlayer player) {

    }

    @Override
    public void use(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(ModEffects.STOMPING.get(), Integer.MAX_VALUE, -1));
    }

    @Override
    public void useTurnover(ServerPlayer player) {

    }

    @Override
    public boolean canUseTurnover(ServerPlayer player) {
        return false;
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
