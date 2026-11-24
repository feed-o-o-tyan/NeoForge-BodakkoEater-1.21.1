package net.feed_o_o.bodakkoeater.menu;

import net.feed_o_o.bodakkoeater.BodakkoEater;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(BuiltInRegistries.MENU, BodakkoEater.MOD_ID);

    public static final Supplier<MenuType<NutrientDeviceMenu>> NUTRIENT_DEVICE =
            MENUS.register("nutrient_device",
                    () -> new MenuType<>(NutrientDeviceMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
