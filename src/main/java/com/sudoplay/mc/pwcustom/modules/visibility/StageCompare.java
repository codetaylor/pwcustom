package com.sudoplay.mc.pwcustom.modules.visibility;

import net.darkhax.bookshelf.lib.ItemStackMap;
import net.darkhax.bookshelf.util.StackUtils;
import net.minecraft.item.ItemStack;

/**
 * https://github.com/Darkhax-Minecraft/ItemStages/blob/master/src/main/java/net/darkhax/itemstages/StageCompare.java
 */
public class StageCompare
    implements ItemStackMap.IStackComparator {

  public static final ItemStackMap.IStackComparator INSTANCE = new StageCompare();

  @Override
  public boolean isValid(ItemStack entryStack, Object second) {

    if (second instanceof ItemStack) {

      final ItemStack stack = (ItemStack) second;
      return !this.isTagEmpty(stack) && StackUtils.areStacksSimilarWithPartialNBT(entryStack, stack) || this.isTagEmpty(
          stack) && this.isTagEmpty(entryStack) && StackUtils
          .areStacksSimilar(stack, entryStack);
    }

    return false;
  }

  private boolean isTagEmpty(ItemStack stack) {

    return !stack.hasTagCompound() || stack.getTagCompound().getKeySet().isEmpty();
  }
}