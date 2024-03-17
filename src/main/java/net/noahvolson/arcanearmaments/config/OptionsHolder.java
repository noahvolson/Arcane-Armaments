package net.noahvolson.arcanearmaments.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class OptionsHolder
{
    public static class Common
    {
        public ClassConfig mageConfig;
        public ClassConfig rogueConfig;
        public ClassConfig warriorConfig;
        public ClassConfig clericConfig;

        public Common(ForgeConfigSpec.Builder builder)
        {

            // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> MAGE <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            builder.push("MAGE");
            builder.push("fireball");
            SkillConfig fireball = new SkillConfig(
                    builder.worldRestart().define("hit_damage",            SkillDefaults.FIREBALL.getDamage()),
                    null,
                    builder.worldRestart().define("burn_duration",          SkillDefaults.FIREBALL.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.FIREBALL.getCooldown()),
                    null
            );
            builder.pop();

            builder.push("blink");
            SkillConfig blink = new SkillConfig(
                    builder.worldRestart().define("hit_damage",            SkillDefaults.BLINK.getDamage()),
                    null,
                    builder.worldRestart().define("travel_ticks",          SkillDefaults.BLINK.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.BLINK.getCooldown()),
                    null
            );
            builder.pop();

            builder.push("blizzard");
            SkillConfig blizzard = new SkillConfig(
                    builder.worldRestart().define("hit_damage",            SkillDefaults.BLIZZARD.getDamage()),
                    null,
                    builder.worldRestart().define("duration",          SkillDefaults.BLIZZARD.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.BLIZZARD.getCooldown()),
                    null
            );
            builder.pop();

            builder.push("thunder");
            SkillConfig thunder = new SkillConfig(
                    builder.worldRestart().define("hit_damage",            SkillDefaults.THUNDER.getDamage()),
                    null,
                    builder.worldRestart().define("duration",          SkillDefaults.THUNDER.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.THUNDER.getCooldown()),
                    null
            );
            builder.pop();
            builder.pop();

            // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ROGUE <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            builder.push("ROGUE");
            builder.push("envenom");
            SkillConfig envenom = new SkillConfig(
                    builder.worldRestart().define("hit_damage",            SkillDefaults.ENVENOM.getDamage()),
                    null,
                    builder.worldRestart().define("duration",          SkillDefaults.ENVENOM.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.ENVENOM.getCooldown()),
                    null
            );
            builder.pop();

            builder.push("smoke_bomb");
            SkillConfig smoke_bomb = new SkillConfig(
                    null,
                    null,
                    builder.worldRestart().define("duration",          SkillDefaults.SMOKE_BOMB.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.SMOKE_BOMB.getCooldown()),
                    null
            );
            builder.pop();

            builder.push("rupture");
            SkillConfig rupture = new SkillConfig(
                    builder.worldRestart().define("hit_damage",            SkillDefaults.RUPTURE.getDamage()),
                    null,
                    builder.worldRestart().define("duration",          SkillDefaults.RUPTURE.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.RUPTURE.getCooldown()),
                    null
            );
            builder.pop();

            builder.push("execute");
            SkillConfig execute = new SkillConfig(
                    builder.worldRestart().define("hit_damage",            SkillDefaults.EXECUTE.getDamage()),
                    null,
                    null,
                    builder.worldRestart().define("cooldown",          SkillDefaults.EXECUTE.getCooldown()),
                    null
            );
            builder.pop();
            builder.pop();

            // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> WARRIOR <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            builder.push("WARRIOR");
            builder.push("berserk");
            SkillConfig berserk = new SkillConfig(
                    builder.worldRestart().define("hit_damage",            SkillDefaults.BERSERK.getDamage()),
                    null,
                    builder.worldRestart().define("duration",          SkillDefaults.BERSERK.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.BERSERK.getCooldown()),
                    builder.worldRestart().define("swing_cooldown", SkillDefaults.BERSERK.getTurnoverCooldown())
            );
            builder.pop();

            builder.push("stomp");
            SkillConfig stomp = new SkillConfig(
                    null,
                    null,
                    builder.worldRestart().define("duration",          SkillDefaults.STOMP.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.STOMP.getCooldown()),
                    null
            );
            builder.pop();

            builder.push("grapple");
            SkillConfig grapple = new SkillConfig(
                    builder.worldRestart().define("hit_damage",            SkillDefaults.GRAPPLE.getDamage()),
                    null,
                    null,
                    builder.worldRestart().define("cooldown",          SkillDefaults.GRAPPLE.getCooldown()),
                    null
            );
            builder.pop();

            builder.push("molten_shell");
            SkillConfig molten_shell = new SkillConfig(
                    null,
                    builder.worldRestart().define("regeneration",      SkillDefaults.MOLTEN_SHELL.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.MOLTEN_SHELL.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.MOLTEN_SHELL.getCooldown()),
                    builder.worldRestart().define("burn_duration",     SkillDefaults.MOLTEN_SHELL.getTurnoverCooldown())
            );
            builder.pop();
            builder.pop();

            // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> CLERIC <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            builder.push("CLERIC");
            builder.push("blessed_blades");
            SkillConfig blessed_blades = new SkillConfig(
                    builder.worldRestart().define("hit_damage",            SkillDefaults.BLESSED_BLADES.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.BLESSED_BLADES.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.BLESSED_BLADES.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.BLESSED_BLADES.getCooldown()),
                    null
            );
            builder.pop();

            builder.push("holy_shield");
            SkillConfig holy_shield = new SkillConfig(
                    null,
                    null,
                    builder.worldRestart().define("duration",          SkillDefaults.HOLY_SHIELD.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.HOLY_SHIELD.getCooldown()),
                    null
            );
            builder.pop();

            builder.push("smiting_ray");
            SkillConfig smiting_ray = new SkillConfig(
                    builder.worldRestart().define("hit_damage",            SkillDefaults.SMITING_RAY.getDamage()),
                    null,
                    builder.worldRestart().define("duration",          SkillDefaults.SMITING_RAY.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.SMITING_RAY.getCooldown()),
                    null
            );
            builder.pop();

            builder.push("healing_aura");
            SkillConfig healing_aura = new SkillConfig(
                    null,
                    builder.worldRestart().define("healing",           SkillDefaults.HEALING_AURA.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.HEALING_AURA.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.HEALING_AURA.getCooldown()),
                    null
            );
            builder.pop();
            builder.pop();

            mageConfig      = new ClassConfig(fireball, blink, blizzard, thunder);
            rogueConfig     = new ClassConfig(envenom, smoke_bomb, rupture, execute);
            warriorConfig   = new ClassConfig(berserk, stomp, grapple, molten_shell);
            clericConfig    = new ClassConfig(blessed_blades, holy_shield, smiting_ray, healing_aura);
        }
    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static //constructor
    {
        Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
    }
}
