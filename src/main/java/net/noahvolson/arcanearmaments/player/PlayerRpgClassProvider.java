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

public class PlayerRpgClassProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerRpgClass> PLAYER_RPG_CLASS = CapabilityManager.get(new CapabilityToken<PlayerRpgClass>() { });

    private PlayerRpgClass rpgClass = null;
    private final LazyOptional<PlayerRpgClass> optional = LazyOptional.of(this::createPlayerRpgClass);

    private PlayerRpgClass createPlayerRpgClass() {
        if (this.rpgClass == null) {
            this.rpgClass = new PlayerRpgClass();
        }

        return this.rpgClass;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_RPG_CLASS) {
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
