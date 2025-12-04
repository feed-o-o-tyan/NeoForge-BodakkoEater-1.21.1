package net.feed_o_o.bodakkoeater.item;

import net.feed_o_o.bodakkoeater.BodakkoEater;
import net.feed_o_o.bodakkoeater.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BodakkoEater.MOD_ID);

    public static final Supplier<CreativeModeTab> BODAKKO_TAB = CREATIVE_MODE_TAB.register("bodakko_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.COOKED_BODAKKO.get()))
                    .title(Component.translatable("creativetab.bodakkoeater.bodakko_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.COOKED_BODAKKO);
                        output.accept(ModItems.BODAKKO);
                        output.accept(ModItems.SALT);
                        output.accept(ModItems.POUCHED_WATER);
                        output.accept(ModBlocks.BLOCK_BODAKKO);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
