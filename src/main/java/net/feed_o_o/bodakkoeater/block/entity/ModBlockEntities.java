package net.feed_o_o.bodakkoeater.block.entity;

import net.feed_o_o.bodakkoeater.BodakkoEater;
import net.feed_o_o.bodakkoeater.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BodakkoEater.MOD_ID);

    public static final Supplier<BlockEntityType<NutrientDeviceBlockEntity>> NUTRIENT_DEVICE =
            BLOCK_ENTITIES.register("nutrient_device", () ->
                    BlockEntityType.Builder.of(NutrientDeviceBlockEntity::new,
                            ModBlocks.NUTRIENT_DEVICE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
