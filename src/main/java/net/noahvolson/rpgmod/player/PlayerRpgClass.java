package net.noahvolson.rpgmod.player;

import net.minecraft.nbt.CompoundTag;
import net.noahvolson.rpgmod.entity.rpgclass.ClassType;

public class PlayerRpgClass {
    private ClassType rpgClass = ClassType.NONE;

    public ClassType getRpgClass() {
        return rpgClass;
    }

    public void setRpgClass(ClassType classType) {
        this.rpgClass = classType;
    }

    public void copyFrom(PlayerRpgClass source) {
        this.rpgClass = source.rpgClass;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putString("rpg_class", rpgClass.name());
    }

    public void loadNBTData(CompoundTag nbt) {
        try {
            rpgClass = ClassType.valueOf(nbt.getString("rpg_class"));
        } catch (Exception e) {
            rpgClass = ClassType.NONE;
        }
    }
}
