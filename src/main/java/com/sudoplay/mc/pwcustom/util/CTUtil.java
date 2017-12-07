package com.sudoplay.mc.pwcustom.util;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CTUtil {

  public static Ingredient toIngredient(IIngredient ingredient) {

    return new CTIngredient(ingredient);
  }

  public static Ingredient[][] toIngredientMatrix(IIngredient[][] ingredients) {

    Ingredient[][] result = new Ingredient[ingredients.length][];

    for (int row = 0; row < ingredients.length; row++) {
      result[row] = new Ingredient[ingredients[row].length];

      for (int col = 0; col < ingredients[row].length; col++) {
        result[row][col] = new CTIngredient(ingredients[row][col]);
      }
    }

    return result;
  }

  public static Ingredient[] toIngredientArray(IIngredient[] ingredients) {

    Ingredient[] result = new Ingredient[ingredients.length];

    for (int i = 0; i < ingredients.length; i++) {
      result[i] = new CTIngredient(ingredients[i]);
    }

    return result;
  }

  public static class CTIngredient
      extends Ingredient {

    private IIngredient ingredient;

    public CTIngredient(IIngredient ingredient) {

      this.ingredient = ingredient;
    }

    @Override
    public ItemStack[] getMatchingStacks() {

      List<IItemStack> stacks = this.ingredient != null ? this.ingredient.getItems() : Collections.emptyList();
      return InputHelper.toStacks(stacks.toArray(new IItemStack[stacks.size()]));
    }

    @Override
    public boolean apply(@Nullable ItemStack itemStack) {

      if (this.ingredient == null) {
        return itemStack == null || itemStack.isEmpty();
      }
      return this.ingredient.matches(InputHelper.toIItemStack(itemStack));
    }
  }

}
