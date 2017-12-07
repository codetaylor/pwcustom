package com.sudoplay.mc.pwcustom.recipe;

import com.sudoplay.mc.pwcustom.inventory.CraftingMatrixStackHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class RecipeWorkbenchShapeless
    implements IRecipeWorkbench {

  private ItemStack tool;
  private NonNullList<Ingredient> input;
  private ItemStack result;
  private int toolDamage;

  public RecipeWorkbenchShapeless(
      ItemStack tool,
      NonNullList<Ingredient> input,
      ItemStack result,
      int toolDamage
  ) {

    this.tool = tool;
    this.input = input;
    this.result = result;
    this.toolDamage = toolDamage;
  }

  @Override
  public ItemStack getResult() {

    return this.result.copy();
  }

  @Override
  public int getToolDamage() {

    return this.toolDamage;
  }

  @Override
  public boolean matches(ItemStack tool, CraftingMatrixStackHandler craftingMatrix) {

    // Do we have the correct tool?
    if (!this.tool.isItemEqualIgnoreDurability(tool)) {
      return false;
    }

    // Does the tool have enough durability for this recipe?
    /*if (tool.getItemDamage() + this.toolDamage > tool.getMaxDamage()) {
      return false;
    }*/

    return this.recipeLayoutMatchesShapeless(craftingMatrix);
  }

  private boolean recipeLayoutMatchesShapeless(CraftingMatrixStackHandler craftingMatrix) {

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
