package com.sudoplay.mc.pwcustom.recipe;

import com.sudoplay.mc.pwcustom.inventory.CraftingMatrixStackHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RegistryRecipeWorkbenchBasic {

  private List<IRecipeWorkbench> recipeShapedList;
  private List<IRecipeWorkbench> recipeShapelessList;

  public RegistryRecipeWorkbenchBasic() {

    this.recipeShapedList = new ArrayList<>();
    this.recipeShapelessList = new ArrayList<>();
  }

  public IRecipeWorkbench addRecipeShaped(
      ItemStack result,
      ItemStack tool,
      Ingredient[][] input,
      int toolDamage,
      boolean mirrored
  ) {

    NonNullList<Ingredient> inputList = NonNullList.create();
    int width = 0;

    for (Ingredient[] row : input) {

      if (row.length > width) {
        width = row.length;
      }

      Collections.addAll(inputList, row);
    }

    int height = input.length;

    RecipeWorkbenchShaped recipe = new RecipeWorkbenchShaped(
        width,
        height,
        tool,
        inputList,
        result,
        toolDamage,
        mirrored
    );

    this.recipeShapedList.add(recipe);
    return recipe;
  }

  public IRecipeWorkbench addRecipeShapeless(
      ItemStack result,
      ItemStack tool,
      Ingredient[] input,
      int toolDamage
  ) {

    NonNullList<Ingredient> inputList = NonNullList.create();

    inputList.addAll(Arrays.asList(input));

    IRecipeWorkbench recipe = new RecipeWorkbenchShapeless(
        tool,
        inputList,
        result,
        toolDamage
    );

    this.recipeShapelessList.add(recipe);
    return recipe;
  }

  @Nullable
  public IRecipeWorkbench findRecipe(ItemStack tool, CraftingMatrixStackHandler craftingMatrix) {

    for (IRecipeWorkbench recipe : this.recipeShapedList) {

      if (recipe.matches(tool, craftingMatrix)) {
        return recipe;
      }
    }

    for (IRecipeWorkbench recipe : this.recipeShapelessList) {

      if (recipe.matches(tool, craftingMatrix)) {
        return recipe;
      }
    }

    return null;
  }
}
