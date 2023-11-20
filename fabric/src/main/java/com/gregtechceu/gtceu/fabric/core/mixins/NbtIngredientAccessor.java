package com.gregtechceu.gtceu.fabric.core.mixins;

import net.fabricmc.fabric.impl.recipe.ingredient.builtin.NbtIngredient;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = NbtIngredient.class, remap = false)
public interface NbtIngredientAccessor {
    @Accessor
    CompoundTag getNbt();
}
