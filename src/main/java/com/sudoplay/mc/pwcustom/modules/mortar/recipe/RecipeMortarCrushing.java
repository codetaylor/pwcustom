package com.sudoplay.mc.pwcustom.modules.mortar.recipe;

import com.sudoplay.mc.pwcustom.lib.IRecipeOutputProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class RecipeMortarCrushing
    implements IRecipeOutputProvider {

  private Ingredient input;
  private ItemStack output;

  public RecipeMortarCrushing(ItemStack output, Ingredient input) {

    this.input = input;
    this.output = output;
  }

  @Override
  public ItemStack getOutput() {

    return this.output.copy();
  }

  public Ingredient getInput() {

    return this.input;
  }

  public boolean matches(ItemStack input) {

    return this.input.apply(input);
  }
}
