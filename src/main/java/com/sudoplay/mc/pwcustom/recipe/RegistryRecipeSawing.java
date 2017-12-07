package com.sudoplay.mc.pwcustom.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RegistryRecipeSawing {

  private List<RecipeSawing> recipeList;

  public RegistryRecipeSawing() {

    this.recipeList = new ArrayList<>();
  }

  @Nonnull
  public RecipeSawing addRecipe(ItemStack[] drops, ItemStack saw, Ingredient block) {

    RecipeSawing recipe = new RecipeSawing(saw, block, drops);
    this.recipeList.add(recipe);
    return recipe;
  }

  @Nullable
  public RecipeSawing findRecipe(ItemStack saw, ItemStack block) {

    for (RecipeSawing recipe : this.recipeList) {

      if (recipe.matches(saw, block)) {
        return recipe;
      }
    }

    return null;
  }
}
