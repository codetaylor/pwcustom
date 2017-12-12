package com.sudoplay.mc.pwcustom.modules.workbench.recipe;

import com.sudoplay.mc.pwcustom.modules.workbench.gui.CraftingMatrixStackHandler;
import com.sudoplay.mc.pwcustom.lib.IRecipeOutputProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public interface IRecipeWorkbench
    extends IRecipeOutputProvider {

  boolean matches(ItemStack tool, CraftingMatrixStackHandler craftingMatrix);

  int getToolDamage();

  boolean isValidTool(ItemStack tool);

  ItemStack[] getTools();

  NonNullList<Ingredient> getIngredients();
}
