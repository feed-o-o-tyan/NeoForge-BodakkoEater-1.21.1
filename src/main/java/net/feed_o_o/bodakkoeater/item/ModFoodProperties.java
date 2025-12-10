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

    public static final FoodProperties QUADRUPLE_BLOCK_BODAKKO = new FoodProperties.Builder().nutrition(128).saturationModifier(14.40f)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 1600, 0), 1.00f)
            .build();

    public static final FoodProperties QUINTUPLE_BLOCK_BODAKKO = new FoodProperties.Builder().nutrition(256).saturationModifier(28.80f)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 3200, 0), 1.00f)
            .build();

    public static final FoodProperties SEXTUPLE_BLOCK_BODAKKO = new FoodProperties.Builder().nutrition(512).saturationModifier(57.60f)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 6400, 0), 1.00f)
            .build();

    public static final FoodProperties SEPTUPLE_BLOCK_BODAKKO = new FoodProperties.Builder().nutrition(1024).saturationModifier(115.20f)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 12800, 0), 1.00f)
            .build();

    public static final FoodProperties OCTUPLE_BLOCK_BODAKKO = new FoodProperties.Builder().nutrition(2048).saturationModifier(230.40f)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 25600, 0), 1.00f)
            .build();

    public static final FoodProperties NONUPLE_BLOCK_BODAKKO = new FoodProperties.Builder().nutrition(4096).saturationModifier(460.80f)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 0), 1.00f)
            .build();
}
