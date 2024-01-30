package net.noahvolson.rpgmod.config;

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
                    builder.worldRestart().define("damage",            SkillDefaults.FIREBALL.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.FIREBALL.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.FIREBALL.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.FIREBALL.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.FIREBALL.getTurnoverCooldown())
            );
            builder.pop();

            builder.push("blink");
            SkillConfig blink = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.BLINK.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.BLINK.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.BLINK.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.BLINK.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.BLINK.getTurnoverCooldown())
            );
            builder.pop();

            builder.push("blizzard");
            SkillConfig blizzard = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.BLIZZARD.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.BLIZZARD.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.BLIZZARD.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.BLIZZARD.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.BLIZZARD.getTurnoverCooldown())
            );
            builder.pop();

            builder.push("thunder");
            SkillConfig thunder = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.THUNDER.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.THUNDER.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.THUNDER.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.THUNDER.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.THUNDER.getTurnoverCooldown())
            );
            builder.pop();
            builder.pop();

            // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ROGUE <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            builder.push("ROGUE");
            builder.push("envenom");
            SkillConfig envenom = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.ENVENOM.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.ENVENOM.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.ENVENOM.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.ENVENOM.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.ENVENOM.getTurnoverCooldown())
            );
            builder.pop();

            builder.push("smoke_bomb");
            SkillConfig smoke_bomb = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.SMOKE_BOMB.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.SMOKE_BOMB.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.SMOKE_BOMB.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.SMOKE_BOMB.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.SMOKE_BOMB.getTurnoverCooldown())
            );
            builder.pop();

            builder.push("rupture");
            SkillConfig rupture = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.RUPTURE.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.RUPTURE.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.RUPTURE.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.RUPTURE.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.RUPTURE.getTurnoverCooldown())
            );
            builder.pop();

            builder.push("execute");
            SkillConfig execute = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.EXECUTE.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.EXECUTE.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.EXECUTE.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.EXECUTE.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.EXECUTE.getTurnoverCooldown())
            );
            builder.pop();
            builder.pop();

            // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> WARRIOR <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            builder.push("WARRIOR");
            builder.push("berserk");
            SkillConfig berserk = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.BERSERK.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.BERSERK.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.BERSERK.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.BERSERK.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.BERSERK.getTurnoverCooldown())
            );
            builder.pop();

            builder.push("stomp");
            SkillConfig stomp = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.STOMP.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.STOMP.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.STOMP.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.STOMP.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.STOMP.getTurnoverCooldown())
            );
            builder.pop();

            builder.push("grapple");
            SkillConfig grapple = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.GRAPPLE.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.GRAPPLE.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.GRAPPLE.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.GRAPPLE.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.GRAPPLE.getTurnoverCooldown())
            );
            builder.pop();

            builder.push("molten_shell");
            SkillConfig molten_shell = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.MOLTEN_SHELL.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.MOLTEN_SHELL.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.MOLTEN_SHELL.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.MOLTEN_SHELL.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.MOLTEN_SHELL.getTurnoverCooldown())
            );
            builder.pop();
            builder.pop();

            // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> CLERIC <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            builder.push("CLERIC");
            builder.push("blessed_blades");
            SkillConfig blessed_blades = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.BLESSED_BLADES.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.BLESSED_BLADES.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.BLESSED_BLADES.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.BLESSED_BLADES.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.BLESSED_BLADES.getTurnoverCooldown())
            );
            builder.pop();

            builder.push("holy_shield");
            SkillConfig holy_shield = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.HOLY_SHIELD.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.HOLY_SHIELD.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.HOLY_SHIELD.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.HOLY_SHIELD.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.HOLY_SHIELD.getTurnoverCooldown())
            );
            builder.pop();

            builder.push("smiting_ray");
            SkillConfig smiting_ray = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.SMITING_RAY.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.SMITING_RAY.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.SMITING_RAY.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.SMITING_RAY.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.SMITING_RAY.getTurnoverCooldown())
            );
            builder.pop();

            builder.push("healing_aura");
            SkillConfig healing_aura = new SkillConfig(
                    builder.worldRestart().define("damage",            SkillDefaults.HEALING_AURA.getDamage()),
                    builder.worldRestart().define("healing",           SkillDefaults.HEALING_AURA.getHealing()),
                    builder.worldRestart().define("duration",          SkillDefaults.HEALING_AURA.getDuration()),
                    builder.worldRestart().define("cooldown",          SkillDefaults.HEALING_AURA.getCooldown()),
                    builder.worldRestart().define("turnover_cooldown", SkillDefaults.HEALING_AURA.getTurnoverCooldown())
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
