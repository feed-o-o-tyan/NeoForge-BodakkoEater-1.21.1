package net.feed_o_o.bodakkoeater.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class EatableBlockItem extends BlockItem {

    public EatableBlockItem(Block block, Properties props, FoodProperties food) {
        super(block, props.food(food));
    }
}

