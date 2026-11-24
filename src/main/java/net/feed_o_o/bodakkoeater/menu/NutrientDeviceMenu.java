package net.feed_o_o.bodakkoeater.menu;

import net.feed_o_o.bodakkoeater.block.ModBlocks;
import net.feed_o_o.bodakkoeater.block.entity.MachineItemHandler;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

/** One row of 9 food slots + the player's inventory (same layout as a 1-row chest) + 5 face buttons. */
public class NutrientDeviceMenu extends AbstractContainerMenu {
    public static final int MACHINE_SLOTS = MachineItemHandler.SLOT_COUNT;
    /** Number of face buttons (every face except the top). */
    public static final int FACE_COUNT = 5;

    private final ContainerLevelAccess access;
    private final ContainerData data;

    /** Client side: the slot contents and the face mask are filled in by the server. */
    public NutrientDeviceMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new MachineItemHandler(() -> {}), new SimpleContainerData(1),
                ContainerLevelAccess.NULL);
    }

    /** Server side. */
    public NutrientDeviceMenu(int containerId, Inventory inventory, IItemHandler handler,
                                 ContainerData data, ContainerLevelAccess access) {
        super(ModMenuTypes.NUTRIENT_DEVICE.get(), containerId);
        this.access = access;
        this.data = data;

        for (int i = 0; i < MACHINE_SLOTS; i++) {
            addSlot(new SlotItemHandler(handler, i, 8 + i * 18, 18));
        }
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 49 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(inventory, col, 8 + col * 18, 107));
        }
        addDataSlots(data);
    }

    /** Bit i set = input from face i is allowed (0 = bottom, 1 = north, 2 = south, 3 = west, 4 = east). */
    public int getInputMask() {
        return data.get(0);
    }

    /** Called (on the server) when a face button is pressed in the GUI. */
    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id >= 0 && id < FACE_COUNT) {
            data.set(0, data.get(0) ^ (1 << id));
            return true;
        }
        return false;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            copy = stack.copy();
            if (index < MACHINE_SLOTS) {
                if (!this.moveItemStackTo(stack, MACHINE_SLOTS, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, MACHINE_SLOTS, false)) {
                return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModBlocks.NUTRIENT_DEVICE.get());
    }
}
