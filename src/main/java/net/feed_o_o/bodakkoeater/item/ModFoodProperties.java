package net.feed_o_o.bodakkoeater.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties BODAKKO = new FoodProperties.Builder().nutrition(2).saturationModifier(0.15f)
            .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS,800), 1.00f)
            .build();

    public static final FoodProperties COOKED_BODAKKO = new FoodProperties.Builder().nutrition(8).saturationModifier(0.90f)
            .build();

    public static final FoodProperties BLOCK_BODAKKO = new FoodProperties.Builder().nutrition(16).saturationModifier(1.80f)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 200, 0), 1.00f)
            .build();
}
