package com.sudoplay.mc.pwcustom.api;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PWCustomAPI {

  private static final List<SawRecipe> SAW_RECIPE_LIST;

  static {
    SAW_RECIPE_LIST = new ArrayList<>();
  }

  @Nonnull
  public static SawRecipe addSawRecipe(ItemStack[] drops, ItemStack saw, ItemStack block) {

    SawRecipe recipe = new SawRecipe(saw, block, drops);
    SAW_RECIPE_LIST.add(recipe);
    return recipe;
  }

  @Nonnull
  public static SawRecipe getSawRecipe(ItemStack saw, ItemStack block) {

    for (SawRecipe recipe : SAW_RECIPE_LIST) {

      if (ItemStack.areItemsEqualIgnoreDurability(recipe.getSaw(), saw)
          && ItemStack.areItemsEqual(recipe.getBlock(), block)) {
        return recipe;
      }
    }

    return SawRecipe.NULL;
  }

}
