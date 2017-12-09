package com.sudoplay.mc.pwcustom.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public interface IRecipeResultProvider {

  ItemStack[] getTools();

  NonNullList<Ingredient> getIngredients();

  ItemStack getOutput();
}
