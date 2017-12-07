package com.sudoplay.mc.pwcustom.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class RecipeSawing {

  /**
   * The saw used.
   */
  private ItemStack saw;

  /**
   * The dropped item to replace.
   */
  private Ingredient replacedItem;

  /**
   * The items to replace the matched drops with.
   */
  private ItemStack[] replacementItems;

  public RecipeSawing(ItemStack saw, Ingredient replacedItem, ItemStack[] replacementItems) {

    this.saw = saw;
    this.replacedItem = replacedItem;
    this.replacementItems = replacementItems;
  }

  public ItemStack[] getReplacementItems() {

    ItemStack[] result = new ItemStack[this.replacementItems.length];

    for (int i = 0; i < this.replacementItems.length; i++) {
      result[i] = this.replacementItems[i].copy();
    }

    return result;
  }

  public boolean matches(ItemStack saw, ItemStack block) {

    return ItemStack.areItemsEqualIgnoreDurability(this.saw, saw)
        && this.replacedItem.apply(block);
  }
}
