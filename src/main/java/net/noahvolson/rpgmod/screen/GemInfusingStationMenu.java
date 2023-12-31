package net.noahvolson.rpgmod.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.noahvolson.rpgmod.block.ModBlocks;
import net.noahvolson.rpgmod.block.entity.GemInfusingStationBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.noahvolson.rpgmod.entity.skill.SkillType;
import net.noahvolson.rpgmod.networking.ModMessages;
import net.noahvolson.rpgmod.networking.packet.RpgClassSyncS2CPacket;
import net.noahvolson.rpgmod.player.PlayerRpgClassProvider;
import net.noahvolson.rpgmod.player.PlayerUnlockedSkillsProvider;
import net.noahvolson.rpgmod.rpgclass.RpgClass;
import net.noahvolson.rpgmod.rpgclass.RpgClasses;

public class GemInfusingStationMenu extends AbstractContainerMenu {
    public final GemInfusingStationBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private final int[] buttonDownCounter = {0,0,0,0};

    public GemInfusingStationMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public GemInfusingStationMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.GEM_INFUSING_STATION_MENU.get(), id);
        checkContainerSize(inv, 2);
        blockEntity = (GemInfusingStationBlockEntity) entity;
        this.level = inv.player.level;
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
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
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

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
                player, ModBlocks.GEM_INFUSING_STATION.get());
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

    public boolean renderPressedButton(int index) {
        if (buttonDownCounter[index] > 0) {
            buttonDownCounter[index]--;
            return true;
        }
        return false;
    }

    public RpgClass getRpgClass() {
        return RpgClasses.getById(this.data.get(0));
    }

    private void unlockSkill(Player player, SkillType skill) {
        player.getCapability(PlayerUnlockedSkillsProvider.PLAYER_UNLOCKED_SKILLS).ifPresent(unlockedSkills -> {
            if (!unlockedSkills.contains(skill)) {
                unlockedSkills.setPlayerUnlockedSkills(unlockedSkills.getPlayerUnlockedSkills() + "[" + skill.name() + "]");
            }
        });
    }

    // For testing purposes
    private void lockAllSkills(Player player) {
        player.getCapability(PlayerUnlockedSkillsProvider.PLAYER_UNLOCKED_SKILLS).ifPresent(unlockedSkills -> {
            unlockedSkills.setPlayerUnlockedSkills("");
        });
    }

    @Override
    public boolean clickMenuButton(Player player, int button) {
        this.blockEntity.craftItem();
        System.out.println("n9v9o9 clickMenuButton - " + button + " client side " + player.level.isClientSide());
        switch (button) {
            case 0 -> {
                buttonDownCounter[0] = 20;
                lockAllSkills(player);
                return true;
            }
            case 1 -> {
                buttonDownCounter[1] = 20;
                unlockSkill(player, getRpgClass().getSkill2());
                return true;
            }
            case 2 -> {
                buttonDownCounter[2] = 20;
                unlockSkill(player, getRpgClass().getSkill3());
                return true;
            }
            case 3 -> {
                buttonDownCounter[3] = 20;
                unlockSkill(player, getRpgClass().getSkill4());
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}