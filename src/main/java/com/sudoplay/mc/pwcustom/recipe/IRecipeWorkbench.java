package com.sudoplay.mc.pwcustom.recipe;

import com.sudoplay.mc.pwcustom.inventory.CraftingMatrixStackHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public interface IRecipeWorkbench
    extends IRecipeResultProvider {

  boolean matches(ItemStack tool, CraftingMatrixStackHandler craftingMatrix);

  int getToolDamage();
}
