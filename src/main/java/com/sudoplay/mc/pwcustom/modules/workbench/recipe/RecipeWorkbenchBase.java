package com.sudoplay.mc.pwcustom.modules.workbench.recipe;

import com.sudoplay.mc.pwcustom.modules.workbench.gui.CraftingMatrixStackHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public abstract class RecipeWorkbenchBase
    implements IRecipeWorkbench {

  protected ItemStack[] tools;
  protected ItemStack output;
  protected int toolDamage;
  protected NonNullList<Ingredient> ingredients;

  /* package */ RecipeWorkbenchBase(
      ItemStack output,
      ItemStack[] tools,
      int toolDamage,
      NonNullList<Ingredient> ingredients
  ) {

    this.output = output;
    this.tools = tools;
    this.toolDamage = toolDamage;
    this.ingredients = ingredients;
  }

  @Override
  public boolean isValidTool(ItemStack tool) {

    for (ItemStack itemStack : this.tools) {

      if (itemStack.isItemEqualIgnoreDurability(tool)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public ItemStack[] getTools() {

    return this.tools;
  }

  @Override
  public NonNullList<Ingredient> getIngredients() {

    return this.ingredients;
  }

  @Override
  public ItemStack getOutput() {

    return this.output.copy();
  }

  @Override
  public int getToolDamage() {

    return this.toolDamage;
  }

  @Override
  public boolean matches(ItemStack tool, CraftingMatrixStackHandler craftingMatrix) {

    // Do we have the correct tool?
    if (!this.isValidTool(tool)) {
      return false;
    }

    // Does the tool have enough durability for this recipe?
    /*if (tool.getItemDamage() + this.toolDamage > tool.getMaxDamage()) {
      return false;
    }*/

    return this.matches(craftingMatrix);
  }

  protected abstract boolean matches(CraftingMatrixStackHandler craftingMatrix);
}
