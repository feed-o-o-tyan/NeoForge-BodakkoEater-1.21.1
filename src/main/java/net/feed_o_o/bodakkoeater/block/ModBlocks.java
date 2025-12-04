package net.feed_o_o.bodakkoeater.block;

import net.feed_o_o.bodakkoeater.BodakkoEater;
import net.feed_o_o.bodakkoeater.item.ModFoodProperties;
import net.feed_o_o.bodakkoeater.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BodakkoEater.MOD_ID);

    public static final DeferredBlock<Block> BLOCK_BODAKKO = registerEatableBlock("block_bodakko",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.STONE)),

            );

    private static <T extends Block> DeferredBlock<T> registerEatableBlock(String nameeat, Supplier<T> block, FoodProperties food) {
        DeferredBlock<T> toReturn = BLOCKS.register(nameeat, block);
        registerEatableBlockItem(nameeat, toReturn, food);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerEatableBlockItem(String nameeat, DeferredBlock<T> block, FoodProperties food) {
        ModItems.ITEMS.register(nameeat,
                () -> new BlockItem(block.get(), new Item.Properties().food(food)));
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
