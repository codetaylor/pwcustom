package com.sudoplay.mc.pwcustom.modules.mortar.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegistryRecipeMortar {

  private List<RecipeMortar> recipeList;

  public RegistryRecipeMortar() {

    this.recipeList = new ArrayList<>();
  }

  @Nonnull
  public RecipeMortar addRecipe(ItemStack output, Ingredient[] inputs) {

    NonNullList<Ingredient> inputList = NonNullList.create();
    Collections.addAll(inputList, inputs);
    RecipeMortar recipe = new RecipeMortar(output, inputList);
    this.recipeList.add(recipe);
    return recipe;
  }

  @Nullable
  public RecipeMortar findRecipe(ItemStack[] inputs) {

    for (RecipeMortar recipe : this.recipeList) {

      if (recipe.matches(inputs)) {
        return recipe;
      }
    }

    return null;
  }
}
