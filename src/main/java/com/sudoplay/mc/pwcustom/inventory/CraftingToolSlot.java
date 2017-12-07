package com.sudoplay.mc.pwcustom.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class CraftingToolSlot
    extends SlotItemHandler {

  public CraftingToolSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {

    super(itemHandler, index, xPosition, yPosition);
  }

  @Override
  public boolean isItemValid(@Nonnull ItemStack stack) {

    return !stack.isEmpty()
        && !stack.getItem().getHasSubtypes()
        && stack.getItem().getMaxDamage() > 0;
  }
}
