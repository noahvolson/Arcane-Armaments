package net.noahvolson.rpgmod.client;

import net.noahvolson.rpgmod.entity.skill.SkillType;

import java.util.ArrayList;

public class ClientUnlockedSkillsData {
    private static ArrayList<SkillType> clientUnlockedSkills;

    public static void set(String serialized) {
        for (SkillType skill : SkillType.values()) {
            if (serialized.contains(skill.name())) {
                clientUnlockedSkills.add(skill);
            }
        }
    }

    public static ArrayList<SkillType> getUnlockedSkills() {
        return clientUnlockedSkills;
    }

}
