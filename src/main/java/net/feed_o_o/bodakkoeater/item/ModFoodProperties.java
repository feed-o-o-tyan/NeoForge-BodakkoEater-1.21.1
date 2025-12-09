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

    public static final FoodProperties DOUBLE_BLOCK_BODAKKO = new FoodProperties.Builder().nutrition(32).saturationModifier(3.60f)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 400, 0), 1.00f)
            .build();

    public static final FoodProperties TRIPLE_BLOCK_BODAKKO = new FoodProperties.Builder().nutrition(64).saturationModifier(7.20f)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 800, 0), 1.00f)
            .build();
}
