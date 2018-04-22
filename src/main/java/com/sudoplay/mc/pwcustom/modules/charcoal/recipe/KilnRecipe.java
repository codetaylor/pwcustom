package com.sudoplay.mc.pwcustom.modules.charcoal.recipe;

import com.sudoplay.mc.pwcustom.modules.charcoal.Registries;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;

public class KilnRecipe {

  @Nullable
  public static KilnRecipe getRecipe(ItemStack input) {

    for (KilnRecipe recipe : Registries.KILN_RECIPE_LIST) {

      if (recipe.matches(input)) {
        return recipe;
      }
    }

    return null;
  }

  private final Ingredient input;
  private final ItemStack output;
  private final float failureChance;
  private final ItemStack[] failureItems;

  public KilnRecipe(Ingredient input, ItemStack output, float failureChance, ItemStack[] failureItems) {

    this.input = input;
    this.output = output;
    this.failureChance = MathHelper.clamp(failureChance, 0, 1);
    this.failureItems = failureItems;
  }

  public Ingredient getInput() {

    return this.input;
  }

  public ItemStack getOutput() {

    return this.output.copy();
  }

  public float getFailureChance() {

    return this.failureChance;
  }

  public ItemStack[] getFailureItems() {

    return this.failureItems;
  }

  public boolean matches(ItemStack input) {

    return this.input.apply(input);
  }
}
