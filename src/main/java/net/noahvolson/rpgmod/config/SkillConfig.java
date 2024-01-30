package net.noahvolson.rpgmod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public record SkillConfig(ForgeConfigSpec.ConfigValue<Integer> damage,
                          ForgeConfigSpec.ConfigValue<Integer> healing,
                          ForgeConfigSpec.ConfigValue<Integer> duration,
                          ForgeConfigSpec.ConfigValue<Integer> cooldown,
                          ForgeConfigSpec.ConfigValue<Integer> turnoverCooldown) {

}
