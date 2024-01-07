package net.noahvolson.rpgmod.client;

import net.noahvolson.rpgmod.entity.skill.SkillType;

public class ClientUnlockedSkillsData {
    private static String clientUnlockedSkills = "";

    public static void set(String unlockedSkills) {
        clientUnlockedSkills = unlockedSkills;
    }

    public static boolean contains(SkillType skill) {
        return clientUnlockedSkills.contains(skill.name());
    }

}
