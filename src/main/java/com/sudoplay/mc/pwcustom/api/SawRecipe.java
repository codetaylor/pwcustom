package com.sudoplay.mc.pwcustom.api;

import net.minecraft.item.ItemStack;

public class SawRecipe {

  public static final SawRecipe NULL = new SawRecipe(ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack[0]);

  private ItemStack saw;
  private ItemStack block;
  private ItemStack[] drops;

  public SawRecipe(ItemStack saw, ItemStack block, ItemStack[] drops) {

    this.saw = saw;
    this.block = block;
    this.drops = drops;
  }

  public ItemStack getSaw() {

    return this.saw;
  }

  public ItemStack getBlock() {

    return this.block;
  }

  public ItemStack[] getDrops() {

    return this.drops;
  }
}
