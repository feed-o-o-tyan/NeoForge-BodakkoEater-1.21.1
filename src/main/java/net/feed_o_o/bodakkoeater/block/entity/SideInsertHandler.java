package net.feed_o_o.bodakkoeater.block.entity;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

/**
 * Item handler exposed to hoppers/pipes on one face of the machine.
 * Insert only: items can be put in when that face's input setting is ON, and can never be taken out.
 */
public class SideInsertHandler implements IItemHandler {
    private final NutrientDeviceBlockEntity machine;
    private final int faceIndex;

    public SideInsertHandler(NutrientDeviceBlockEntity machine, int faceIndex) {
        this.machine = machine;
        this.faceIndex = faceIndex;
    }

    @Override
    public int getSlots() {
        return machine.getItems().getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return machine.getItems().getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!machine.isFaceInputEnabled(faceIndex)) {
            return stack;
        }
        return machine.getItems().insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return machine.getItems().getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return machine.isFaceInputEnabled(faceIndex) && machine.getItems().isItemValid(slot, stack);
    }
}
