package net.noahvolson.arcanearmaments.screen;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.noahvolson.arcanearmaments.block.ModBlocks;
import net.noahvolson.arcanearmaments.block.entity.ArmoryBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.noahvolson.arcanearmaments.networking.ModMessages;
import net.noahvolson.arcanearmaments.networking.packet.UnlockedSkillsSyncS2CPacket;
import net.noahvolson.arcanearmaments.player.PlayerUnlockedSkillsProvider;
import net.noahvolson.arcanearmaments.rpgclass.RpgClass;
import net.noahvolson.arcanearmaments.rpgclass.RpgClasses;
import org.jetbrains.annotations.NotNull;

public class ArmoryMenu extends AbstractContainerMenu {
    public final ArmoryBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public ArmoryMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(ArmoryBlockEntity.dataSize));
    }

    public ArmoryMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.ARMORY_MENU.get(), id);
        checkContainerSize(inv, 2);
        blockEntity = (ArmoryBlockEntity) entity;
        this.level = inv.player.level;
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 13, 47)); // Top left corner
            this.addSlot(new SlotItemHandler(handler, 1, 33, 47));
        });

        addDataSlots(data);
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    public static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 2;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.ARMORY.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }

    public RpgClass getRpgClass() {
        return RpgClasses.getById(this.data.get(0));
    }

    public boolean getCraftSuccessful() {
        return this.data.get(1) != 0;
    }

    public int getAttemptCounter() {
        return this.data.get(2);
    }

    // For testing purposes
    private void lockAllSkills(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            player.getCapability(PlayerUnlockedSkillsProvider.PLAYER_UNLOCKED_SKILLS).ifPresent(unlockedSkills -> {
                unlockedSkills.setPlayerUnlockedSkills("");
                ModMessages.sendToPlayer(new UnlockedSkillsSyncS2CPacket(unlockedSkills.getPlayerUnlockedSkills()), serverPlayer);
            });
        }
    }

    @Override
    public boolean clickMenuButton(@NotNull Player player, int button) {

        if (button > -1 && button < 4) {
            RpgClass rpgClass = getRpgClass();
            if (rpgClass != null) {
                switch (button) {
                    case 0 -> {
                        // Used for testing during development
                        // lockAllSkills(player);
                    }
                    case 1 -> {
                        this.blockEntity.craftSkill(player, rpgClass.getSkill2());
                    }
                    case 2 -> {
                        this.blockEntity.craftSkill(player, rpgClass.getSkill3());
                    }
                    case 3 -> {
                        this.blockEntity.craftSkill(player, rpgClass.getSkill4());
                    }
                }
            }
            return true;
        }
        return false;
    }
}