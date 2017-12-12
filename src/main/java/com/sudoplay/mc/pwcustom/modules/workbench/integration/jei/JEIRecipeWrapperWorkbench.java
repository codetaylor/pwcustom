package com.sudoplay.mc.pwcustom.modules.workbench.integration.jei;

import com.sudoplay.mc.pwcustom.modules.workbench.recipe.RecipeWorkbenchBase;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JEIRecipeWrapperWorkbench
    implements IRecipeWrapper {

  private RecipeWorkbenchBase recipe;
  private List<List<ItemStack>> inputs;
  private List<ItemStack> tools;

  public JEIRecipeWrapperWorkbench(
      RecipeWorkbenchBase recipe
  ) {

    this.recipe = recipe;
    this.inputs = new ArrayList<>();
    this.tools = new ArrayList<>();

    for (Ingredient input : this.recipe.getIngredients()) {
      this.inputs.add(Arrays.asList(input.getMatchingStacks()));
    }

    this.tools = Arrays.asList(this.recipe.getTools());
  }

  public List<ItemStack> getTools() {

    return this.tools;
  }

  @Override
  public void getIngredients(IIngredients ingredients) {

    ingredients.setInputLists(ItemStack.class, this.inputs);
    ingredients.setOutput(ItemStack.class, this.recipe.getOutput());
  }

  @Override
  public void drawInfo(
      Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY
  ) {

    GlStateManager.pushMatrix();
    //GlStateManager.scale(0.5, 0.5, 1);
    String label = "-" + this.recipe.getToolDamage();
    minecraft.fontRenderer.drawString(label, (95 - 3) - minecraft.fontRenderer.getStringWidth(label) * 0.5f, (55 - 3), 0xFFFFFFFF, true);
    GlStateManager.popMatrix();
  }
}
