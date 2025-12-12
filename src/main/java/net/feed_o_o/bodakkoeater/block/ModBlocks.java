package net.feed_o_o.bodakkoeater.block;

import net.feed_o_o.bodakkoeater.BodakkoEater;
import net.feed_o_o.bodakkoeater.item.EatableBlockItem;
import net.feed_o_o.bodakkoeater.item.ModFoodProperties;
import net.feed_o_o.bodakkoeater.item.ModItems;
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
            ModFoodProperties.BLOCK_BODAKKO
            );

    public static final DeferredBlock<Block> DOUBLE_BLOCK_BODAKKO = registerEatableBlock("double_block_bodakko",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .sound(SoundType.STONE)),
            ModFoodProperties.DOUBLE_BLOCK_BODAKKO
    );

    public static final DeferredBlock<Block> TRIPLE_BLOCK_BODAKKO = registerEatableBlock("triple_block_bodakko",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .sound(SoundType.STONE)),
            ModFoodProperties.TRIPLE_BLOCK_BODAKKO
    );

    public static final DeferredBlock<Block> QUADRUPLE_BLOCK_BODAKKO = registerEatableBlock("quadruple_block_bodakko",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f)
                    .sound(SoundType.STONE)),
            ModFoodProperties.QUADRUPLE_BLOCK_BODAKKO
    );

    public static final DeferredBlock<Block> QUINTUPLE_BLOCK_BODAKKO = registerEatableBlock("quintuple_block_bodakko",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(8f)
                    .sound(SoundType.STONE)),
            ModFoodProperties.QUINTUPLE_BLOCK_BODAKKO
    );

    public static final DeferredBlock<Block> SEXTUPLE_BLOCK_BODAKKO = registerEatableBlock("sextuple_block_bodakko",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(13f)
                    .sound(SoundType.STONE)),
            ModFoodProperties.SEXTUPLE_BLOCK_BODAKKO
    );

    public static final DeferredBlock<Block> SEPTUPLE_BLOCK_BODAKKO = registerEatableBlock("septuple_block_bodakko",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(21f)
                    .sound(SoundType.STONE)),
            ModFoodProperties.SEPTUPLE_BLOCK_BODAKKO
    );

    public static final DeferredBlock<Block> OCTUPLE_BLOCK_BODAKKO = registerEatableBlock("octuple_block_bodakko",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(34f)
                    .sound(SoundType.STONE)),
            ModFoodProperties.OCTUPLE_BLOCK_BODAKKO
    );

    public static final DeferredBlock<Block> NONUPLE_BLOCK_BODAKKO = registerEatableBlock("nonuple_block_bodakko",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(55f)
                    .sound(SoundType.STONE)),
            ModFoodProperties.NONUPLE_BLOCK_BODAKKO
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
                () -> new EatableBlockItem(block.get(), new Item.Properties(), food));
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
