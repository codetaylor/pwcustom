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

  private List<RecipeMortarMixing> recipeListMixing;
  private List<RecipeMortarCrushing> recipeListCrushing;

  public RegistryRecipeMortar() {

    this.recipeListMixing = new ArrayList<>();
    this.recipeListCrushing = new ArrayList<>();
  }

  @Nonnull
  public RecipeMortarMixing addMixingRecipe(ItemStack output, Ingredient[] inputs) {

    NonNullList<Ingredient> inputList = NonNullList.create();
    Collections.addAll(inputList, inputs);
    RecipeMortarMixing recipe = new RecipeMortarMixing(output, inputList);
    this.recipeListMixing.add(recipe);
    return recipe;
  }

  public RecipeMortarCrushing addCrushingRecipe(ItemStack output, Ingredient input) {

    RecipeMortarCrushing recipe = new RecipeMortarCrushing(output, input);
    this.recipeListCrushing.add(recipe);
    return recipe;
  }

  @Nullable
  public RecipeMortarMixing findMixingRecipe(ItemStack[] inputs) {

    for (RecipeMortarMixing recipe : this.recipeListMixing) {

      if (recipe.matches(inputs)) {
        return recipe;
      }
    }

    return null;
  }

  @Nullable
  public RecipeMortarCrushing findCrushingRecipe(ItemStack input) {

    for (RecipeMortarCrushing recipe : this.recipeListCrushing) {

      if (recipe.matches(input)) {
        return recipe;
      }
    }

    return null;
  }
}
