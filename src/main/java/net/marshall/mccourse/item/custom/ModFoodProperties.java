package net.marshall.mccourse.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties KOHLRABI = new FoodProperties.Builder().nutrition(3).saturationMod(2.f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 01f).build();
}
