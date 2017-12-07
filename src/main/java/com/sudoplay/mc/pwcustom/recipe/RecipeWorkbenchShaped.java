package com.sudoplay.mc.pwcustom.recipe;

import com.sudoplay.mc.pwcustom.inventory.CraftingMatrixStackHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public class RecipeWorkbenchShaped
    implements IRecipeWorkbench {

  private final int width;
  private final int height;
  private ItemStack tool;
  private NonNullList<Ingredient> input;
  private ItemStack result;
  private int toolDamage;
  private boolean mirrored;

  public RecipeWorkbenchShaped(
      int width,
      int height,
      ItemStack tool,
      NonNullList<Ingredient> input,
      ItemStack result,
      int toolDamage,
      boolean mirrored
  ) {

    this.width = width;
    this.height = height;
    this.tool = tool;
    this.input = input;
    this.result = result;
    this.toolDamage = toolDamage;
    this.mirrored = mirrored;
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

    return this.recipeLayoutMatchesShaped(craftingMatrix);
  }

  private boolean recipeLayoutMatchesShaped(CraftingMatrixStackHandler craftingMatrix) {

    for (int x = 0; x <= craftingMatrix.getWidth() - this.width; ++x) {

      for (int y = 0; y <= craftingMatrix.getHeight() - this.height; ++y) {

        if (this.checkMatch(craftingMatrix, x, y, false)) {
          return true;
        }

        if (this.mirrored && this.checkMatch(craftingMatrix, x, y, true)) {
          return true;
        }
      }
    }

    return false;
  }

  private boolean checkMatch(CraftingMatrixStackHandler craftingMatrix, int startX, int startY, boolean mirror) {

    for (int x = 0; x < craftingMatrix.getWidth(); ++x) {

      for (int y = 0; y < craftingMatrix.getHeight(); ++y) {

        int subX = x - startX;
        int subY = y - startY;
        Ingredient ingredient = Ingredient.EMPTY;

        if (subX >= 0 && subY >= 0 && subX < this.width && subY < this.height) {

          if (mirror) {
            ingredient = this.input.get(this.width - subX - 1 + subY * this.width);

          } else {
            ingredient = this.input.get(subX + subY * this.width);
          }
        }

        if (!ingredient.apply(craftingMatrix.getStackInSlot(x + y * craftingMatrix.getWidth()))) {
          return false;
        }
      }
    }

    return true;
  }

}
