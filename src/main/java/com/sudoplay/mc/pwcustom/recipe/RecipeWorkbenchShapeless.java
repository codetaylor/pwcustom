package com.sudoplay.mc.pwcustom.recipe;

import com.sudoplay.mc.pwcustom.workbench.gui.CraftingMatrixStackHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class RecipeWorkbenchShapeless
    extends RecipeWorkbenchBase {

  public RecipeWorkbenchShapeless(
      ItemStack[] tools,
      NonNullList<Ingredient> input,
      ItemStack result,
      int toolDamage
  ) {

    super(result, tools, toolDamage, input);
  }

  @Override
  protected boolean matches(CraftingMatrixStackHandler craftingMatrix) {

    int count = 0;
    List<ItemStack> itemList = new ArrayList<>();

    for (int i = 0; i < craftingMatrix.getSlots(); i++) {
      ItemStack itemStack = craftingMatrix.getStackInSlot(i);

      if (!itemStack.isEmpty()) {
        count += 1;
        itemList.add(itemStack);
      }
    }

    if (count != this.input.size()) {
      return false;
    }

    return RecipeMatcher.findMatches(itemList, this.input) != null;
  }

}
