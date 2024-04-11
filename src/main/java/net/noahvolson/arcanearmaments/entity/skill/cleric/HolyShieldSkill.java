package net.noahvolson.arcanearmaments.entity.skill.cleric;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.phys.Vec3;
import net.noahvolson.arcanearmaments.effect.ModEffects;
import net.noahvolson.arcanearmaments.entity.skill.Skill;
import net.noahvolson.arcanearmaments.entity.skill.SkillType;
import net.noahvolson.arcanearmaments.particle.ModParticles;
import net.noahvolson.arcanearmaments.sound.ModSounds;

import java.util.ArrayList;

public class HolyShieldSkill implements Skill {
    private final int RADIUS = 1;

    public HolyShieldSkill(ServerPlayer player) {
    }

    @Override
    public void use(ServerPlayer player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            ArrayList<Vec3> points = getSpherePoints(400, RADIUS);
            for (Vec3 point : points) {
                Vec3 shifted = point.add(player.position());
                serverLevel.sendParticles(ModParticles.HOLY_SHIELD_PARTICLES.get(), shifted.x, shifted.y + 1, shifted.z, 1, 0, 0, 0, 0);
            }
            player.addEffect(new MobEffectInstance(ModEffects.HOLY_SHIELD_3.get(), SkillType.HOLY_SHIELD.getDuration(), 0, false, false, true));
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.HOLY_SHIELD_CAST.get(), SoundSource.HOSTILE, 1F, 1.2F / (player.level().random.nextFloat() * 0.2F + 0.9F));
        }
    }

    private ArrayList<Vec3> getSpherePoints(int samples, int r) {
        ArrayList<Vec3> points = new ArrayList<>();
        double phi = Math.PI * (Math.sqrt(5.) - 1.);        // golden angle in radians

        for (int i = 0; i < samples; i++) {
            float y = 1 - (i / (float)(samples - 1)) * 2;   // y goes from 1 to -1
            double radius = Math.sqrt(1 - y * y);           // radius at y
            double theta = phi * i;
            double x = Math.cos(theta) * radius;
            double z = Math.sin(theta) * radius;
            points.add(new Vec3(x * r, y * r, z * r));
        }
        return points;
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
