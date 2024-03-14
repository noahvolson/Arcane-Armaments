package net.noahvolson.rpgmod.block.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.noahvolson.rpgmod.entity.skill.SkillType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.noahvolson.rpgmod.networking.ModMessages;
import net.noahvolson.rpgmod.networking.packet.UnlockedSkillsSyncS2CPacket;
import net.noahvolson.rpgmod.player.PlayerUnlockedSkillsProvider;
import net.noahvolson.rpgmod.rpgclass.RpgClass;
import net.noahvolson.rpgmod.rpgclass.RpgClasses;
import net.noahvolson.rpgmod.screen.ArmoryMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArmoryBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public final static int dataSize = 3;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;
    private RpgClass rpgClass;
    private boolean craftSuccessful;
    private int attemptCounter = 0;

    public ArmoryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARMORY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0 -> {
                        if (rpgClass != null) {
                            return rpgClass.getId();
                        } else {
                            return -1;
                        }
                    }
                    case 1 -> {
                        return craftSuccessful ? 1 : 0;
                    }
                    case 2 -> {
                        return attemptCounter;
                    }
                    default -> {
                        return -1;
                    }
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> {
                        rpgClass = RpgClasses.getById(value);
                    }
                    case 1 -> {
                        craftSuccessful = value != 0;
                    }
                    case 2 -> {
                        attemptCounter = value;
                    }
                }
            }

            @Override
            public int getCount() {
                return dataSize;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Armory");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ArmoryMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ArmoryBlockEntity pEntity) {
        if(level.isClientSide()) {
            return;
        }
        pEntity.rpgClass = RpgClasses.getByItem(pEntity.itemHandler.getStackInSlot(0).getItem());
    }

    public void craftSkill(Player player, SkillType skill) {
        if(this.level != null && !this.level.isClientSide) {
            attemptCounter++;
            attemptCounter %= 5;
            if(hasRecipe(skill)) {
                this.itemHandler.extractItem(1, 1, false);
                unlockSkill(player, skill);
                craftSuccessful = true;
            } else {
                craftSuccessful = false;
            }
        }
    }

    private boolean hasRecipe(SkillType skill) {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        return this.itemHandler.getStackInSlot(0).getItem() == rpgClass.getClassItem() && this.itemHandler.getStackInSlot(1).getItem() == skill.getCraftCost();
    }

    private void unlockSkill(Player player, SkillType skill) {
        if (player instanceof ServerPlayer serverPlayer) {
            player.getCapability(PlayerUnlockedSkillsProvider.PLAYER_UNLOCKED_SKILLS).ifPresent(unlockedSkills -> {
                if (!unlockedSkills.contains(skill)) {
                    unlockedSkills.setPlayerUnlockedSkills(unlockedSkills.getPlayerUnlockedSkills() + "[" + skill.name() + "]");
                    ModMessages.sendToPlayer(new UnlockedSkillsSyncS2CPacket(unlockedSkills.getPlayerUnlockedSkills()), serverPlayer);
                }
            });
        }
    }
}
