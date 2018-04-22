package com.sudoplay.mc.pwcustom.modules.charcoal.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class KilnRecipe {

  public static final List<KilnRecipe> RECIPE_LIST;

  static {
    RECIPE_LIST = new ArrayList<>();
  }

  @Nullable
  public static KilnRecipe getRecipe(ItemStack input) {

    for (KilnRecipe recipe : RECIPE_LIST) {

      if (recipe.matches(input)) {
        return recipe;
      }
    }

    return null;
  }

  private final Ingredient input;
  private final ItemStack output;
  private final ItemStack[] failureItems;

  public KilnRecipe(Ingredient input, ItemStack output, ItemStack[] failureItems) {

    this.input = input;
    this.output = output;
    this.failureItems = failureItems;
  }

  public Ingredient getInput() {

    return this.input;
  }

  public ItemStack getOutput() {

    return this.output.copy();
  }

  public ItemStack[] getFailureItems() {

    return this.failureItems;
  }

  public boolean matches(ItemStack input) {

    return this.input.apply(input);
  }
}