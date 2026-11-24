package net.feed_o_o.bodakkoeater.block.entity;

import net.feed_o_o.bodakkoeater.menu.NutrientDeviceMenu;
import net.feed_o_o.bodakkoeater.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.List;

/**
 * Every time the machine receives a redstone signal (off -> on), it consumes ONE food from its slots
 * and gives Saturation to every player within range:
 *  - bodakko foods (tag bodakkoeater:bodakko_foods): Saturation III for 15 seconds
 *  - any other food:                                 Saturation I for 5 seconds
 * Foods that give harmful effects (rotten flesh, spider eye, ...) cannot be put in.
 */
public class NutrientDeviceBlockEntity extends BlockEntity implements MenuProvider {
    /** Faces with an input setting, in GUI button order. The top face never accepts items. */
    public static final Direction[] INPUT_FACES =
            {Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
    private static final int ALL_FACES_MASK = (1 << INPUT_FACES.length) - 1;

    /** Range: this many blocks horizontally (each side) and upwards from the top of the machine. */
    private static final int RANGE = 3;

    private static final int STRONG_AMPLIFIER = 2;      // Saturation III
    private static final int STRONG_DURATION = 15 * 20; // 15 seconds
    private static final int NORMAL_AMPLIFIER = 0;      // Saturation I
    private static final int NORMAL_DURATION = 5 * 20;  // 5 seconds

    private final MachineItemHandler items = new MachineItemHandler(this::setChanged);
    private final SideInsertHandler[] sideHandlers = new SideInsertHandler[INPUT_FACES.length];

    /** Bit i = input from INPUT_FACES[i] is allowed. Default: all ON. */
    private int inputMask = ALL_FACES_MASK;
    private boolean wasPowered;

    /** Synced to the GUI (the face buttons read it). */
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return inputMask;
        }

        @Override
        public void set(int index, int value) {
            inputMask = value & ALL_FACES_MASK;
            setChanged();
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public NutrientDeviceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.NUTRIENT_DEVICE.get(), pos, state);
        for (int i = 0; i < INPUT_FACES.length; i++) {
            sideHandlers[i] = new SideInsertHandler(this, i);
        }
    }

    // ---------------------------------------------------------------- food rules

    public static boolean isValidFood(ItemStack stack) {
        if (stack.isEmpty() || stack.is(Items.SUSPICIOUS_STEW)) {
            return false;
        }
        FoodProperties food = stack.get(DataComponents.FOOD);
        if (food == null) {
            return false;
        }
        for (FoodProperties.PossibleEffect possible : food.effects()) {
            if (possible.effect().getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                return false;
            }
        }
        return true;
    }

    // ---------------------------------------------------------------- redstone

    public void setWasPowered(boolean powered) {
        this.wasPowered = powered;
        setChanged();
    }

    /** Server side. Fires once on each off -> on change of the redstone signal. */
    public void onNeighborChanged(Level level, BlockPos pos) {
        boolean powered = level.hasNeighborSignal(pos);
        if (powered == wasPowered) {
            return;
        }
        wasPowered = powered;
        setChanged();
        if (powered) {
            activate(level, pos);
        }
    }

    private void activate(Level level, BlockPos pos) {
        int slot = -1;
        for (int i = 0; i < items.getSlots(); i++) {
            if (!items.getStackInSlot(i).isEmpty()) {
                slot = i;
                break;
            }
        }
        if (slot < 0) {
            return;
        }

        // From the machine's own height up to 3 blocks above its top, 3 blocks to every horizontal side.
        AABB range = new AABB(pos.getX() - RANGE, pos.getY(), pos.getZ() - RANGE,
                pos.getX() + 1 + RANGE, pos.getY() + 1 + RANGE, pos.getZ() + 1 + RANGE);
        List<Player> players = level.getEntitiesOfClass(Player.class, range, player -> !player.isSpectator());
        if (players.isEmpty()) {
            return; // nobody to feed: keep the food
        }

        boolean strong = items.getStackInSlot(slot).is(ModTags.Items.BODAKKO_FOODS);
        int amplifier = strong ? STRONG_AMPLIFIER : NORMAL_AMPLIFIER;
        int duration = strong ? STRONG_DURATION : NORMAL_DURATION;
        for (Player player : players) {
            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, duration, amplifier));
        }
        items.extractItem(slot, 1, false);
    }

    // ---------------------------------------------------------------- inventory / settings

    public MachineItemHandler getItems() {
        return items;
    }

    public boolean isFaceInputEnabled(int faceIndex) {
        return (inputMask >> faceIndex & 1) != 0;
    }

    public void dropContents(Level level, BlockPos pos) {
        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack stack = items.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
    }

    // ---------------------------------------------------------------- GUI

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.bodakkoeater.nutrient_device");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new NutrientDeviceMenu(containerId, inventory, items, data,
                ContainerLevelAccess.create(level, worldPosition));
    }

    // ---------------------------------------------------------------- capability (hoppers, pipes)

    /** Item handler for the given face, or null for the top face / no face (not accessible). */
    public IItemHandler getSideHandler(Direction side) {
        if (side == null) {
            return null;
        }
        for (int i = 0; i < INPUT_FACES.length; i++) {
            if (INPUT_FACES[i] == side) {
                return sideHandlers[i];
            }
        }
        return null;
    }

    // ---------------------------------------------------------------- save / load

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Items", items.serializeNBT(registries));
        tag.putInt("InputMask", inputMask);
        tag.putBoolean("WasPowered", wasPowered);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        items.deserializeNBT(registries, tag.getCompound("Items"));
        if (tag.contains("InputMask")) {
            inputMask = tag.getInt("InputMask") & ALL_FACES_MASK;
        }
        wasPowered = tag.getBoolean("WasPowered");
    }
}
