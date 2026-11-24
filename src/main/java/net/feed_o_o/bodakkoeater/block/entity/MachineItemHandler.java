package net.feed_o_o.bodakkoeater.block.entity;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

/** The machine's 9 food slots. Only foods without harmful effects are accepted. */
public class MachineItemHandler extends ItemStackHandler {
    public static final int SLOT_COUNT = 9;

    private final Runnable onChanged;

    public MachineItemHandler(Runnable onChanged) {
        super(SLOT_COUNT);
        this.onChanged = onChanged;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return NutrientDeviceBlockEntity.isValidFood(stack);
    }

    @Override
    protected void onContentsChanged(int slot) {
        onChanged.run();
    }
}
