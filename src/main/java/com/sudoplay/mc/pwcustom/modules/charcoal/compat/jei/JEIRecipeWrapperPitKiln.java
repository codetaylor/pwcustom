package com.sudoplay.mc.pwcustom.modules.charcoal.compat.jei;

import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.PitKilnRecipe;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JEIRecipeWrapperPitKiln
    extends JEIRecipeWrapperTimed {

  private final List<List<ItemStack>> inputs;
  private final ItemStack output;

  public JEIRecipeWrapperPitKiln(PitKilnRecipe recipe) {

    super(recipe);

    this.inputs = Collections.singletonList(Arrays.asList(recipe.getInput().getMatchingStacks()));
    this.output = recipe.getOutput();
  }

  @Override
  public void getIngredients(@Nonnull IIngredients ingredients) {

    ingredients.setInputLists(ItemStack.class, this.inputs);
    ingredients.setOutput(ItemStack.class, this.output);
  }
}
