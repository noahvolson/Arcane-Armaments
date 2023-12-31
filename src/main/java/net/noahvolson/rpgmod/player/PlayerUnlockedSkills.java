package net.noahvolson.rpgmod.player;

import net.minecraft.nbt.CompoundTag;
import net.noahvolson.rpgmod.entity.skill.SkillType;

public class PlayerUnlockedSkills {
    private String playerUnlockedSkills = "";

    public String getPlayerUnlockedSkills() {
        return playerUnlockedSkills;
    }

    public boolean contains(SkillType skill) {
        return playerUnlockedSkills.contains(skill.name());
    }

    public void setPlayerUnlockedSkills(String playerUnlockedSkills) {
        this.playerUnlockedSkills = playerUnlockedSkills;
    }

    public void copyFrom(PlayerUnlockedSkills source) {
        this.playerUnlockedSkills = source.playerUnlockedSkills;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putString("unlocked_skills", playerUnlockedSkills);
    }

    public void loadNBTData(CompoundTag nbt) {
        playerUnlockedSkills = nbt.getString("unlocked_skills");
    }
}