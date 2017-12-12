package com.sudoplay.mc.pwcustom.lib.util;

import net.minecraft.item.ItemStack;

public class StackUtil {

  public static ItemStack decrease(ItemStack itemStack, int amount, boolean checkContainer) {

    if (itemStack.isEmpty()) {
      return ItemStack.EMPTY;
    }

    itemStack.shrink(amount);

    if (itemStack.getCount() <= 0) {

      if (checkContainer && itemStack.getItem().hasContainerItem(itemStack)) {
        return itemStack.getItem().getContainerItem(itemStack);

      } else {
        return ItemStack.EMPTY;
      }
    }

    return itemStack;
  }

}
