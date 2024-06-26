package net.noahvolson.arcanearmaments.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerUnlockedSkillsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerUnlockedSkills> PLAYER_UNLOCKED_SKILLS = CapabilityManager.get(new CapabilityToken<PlayerUnlockedSkills>() { });

    private PlayerUnlockedSkills unlockedSkills = null;
    private final LazyOptional<PlayerUnlockedSkills> optional = LazyOptional.of(this::createPlayerRpgClass);

    private PlayerUnlockedSkills createPlayerRpgClass() {
        if (this.unlockedSkills == null) {
            this.unlockedSkills = new PlayerUnlockedSkills();
        }

        return this.unlockedSkills;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_UNLOCKED_SKILLS ) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerRpgClass().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerRpgClass().loadNBTData(nbt);
    }
}
