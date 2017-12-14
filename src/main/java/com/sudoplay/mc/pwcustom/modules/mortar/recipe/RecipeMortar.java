package com.sudoplay.mc.pwcustom.modules.mortar.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class RecipeMortar {

  private NonNullList<Ingredient> inputs;
  private ItemStack output;

  public RecipeMortar(ItemStack output, NonNullList<Ingredient> inputs) {

    this.inputs = inputs;
    this.output = output;
  }

  public ItemStack getOutput() {

    return this.output.copy();
  }

  public boolean matches(ItemStack[] inputs) {

    int count = 0;
    List<ItemStack> itemList = new ArrayList<>();

    for (int i = 0; i < inputs.length; i++) {
      ItemStack itemStack = inputs[i];

      if (!itemStack.isEmpty()) {
        count += 1;
        itemList.add(itemStack);
      }
    }

    if (count != this.inputs.size()) {
      return false;
    }

    return RecipeMatcher.findMatches(itemList, this.inputs) != null;
  }
}
