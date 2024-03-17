package net.noahvolson.arcanearmaments.client;

import net.noahvolson.arcanearmaments.entity.skill.SkillType;

public class ClientUnlockedSkillsData {
    private static String clientUnlockedSkills = "";

    public static void set(String unlockedSkills) {
        clientUnlockedSkills = unlockedSkills;
    }

    public static boolean contains(SkillType skill) {
        return clientUnlockedSkills.contains(skill.name());
    }

}
