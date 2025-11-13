package net.feed_o_o.bodakkoeater.item;

import net.feed_o_o.bodakkoeater.BodakkoEater;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BodakkoEater.MOD_ID);

    public static final DeferredItem<Item> BODAKKO = ITEMS.register("bodakko",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
