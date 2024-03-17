package net.noahvolson.arcanearmaments.player;

import net.minecraft.nbt.CompoundTag;

public class PlayerRpgClass {
    private String playerRpgClass = "";

    public String getRpgClass() {
        return playerRpgClass;
    }

    public void setRpgClass(String rpgClass) {
        this.playerRpgClass = rpgClass;
    }

    public void copyFrom(PlayerRpgClass source) {
        this.playerRpgClass = source.playerRpgClass;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putString("rpg_class", playerRpgClass);
    }

    public void loadNBTData(CompoundTag nbt) {
        playerRpgClass = nbt.getString("rpg_class");
    }
}
