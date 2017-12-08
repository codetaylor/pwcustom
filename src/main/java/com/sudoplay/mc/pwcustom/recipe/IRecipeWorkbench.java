package com.sudoplay.mc.pwcustom.recipe;

import com.sudoplay.mc.pwcustom.workbench.gui.CraftingMatrixStackHandler;
import net.minecraft.item.ItemStack;

public interface IRecipeWorkbench
    extends IRecipeResultProvider {

  boolean matches(ItemStack tool, CraftingMatrixStackHandler craftingMatrix);

  int getToolDamage();

  boolean isValidTool(ItemStack tool);

}
