package net.feed_o_o.bodakkoeater.util;

import net.feed_o_o.bodakkoeater.BodakkoEater;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(BodakkoEater.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");
        /** Foods that make the nutrient device give the strong effect (Saturation III, 15 s). */
        public static final TagKey<Item> BODAKKO_FOODS = createTag("bodakko_foods");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(BodakkoEater.MOD_ID, name));
        }
    }
}
