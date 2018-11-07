package com.sudoplay.mc.pwcustom.modules.charcoal.compat.jei;

import com.sudoplay.mc.pwcustom.modules.charcoal.Registries;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleBlocks;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.KilnBrickRecipe;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.KilnPitRecipe;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.PitBurnRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PluginJEI
    implements IModPlugin {

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {

    IJeiHelpers jeiHelpers = registry.getJeiHelpers();
    IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

    registry.addRecipeCategories(
        new JEIRecipeCategoryKilnPit(guiHelper),
        new JEIRecipeCategoryKilnBrick(guiHelper),
        new JEIRecipeCategoryPitBurn(guiHelper),
        new JEIRecipeCategoryRefractoryBurn(guiHelper)
    );
  }

  @Override
  public void register(IModRegistry registry) {

    // Leave as an example in case I decide to add info later.
    /*
    {
      List<ItemStack> outputList = Registries.BURN_RECIPE.getValuesCollection()
          .stream()
          .filter(burnRecipe -> !burnRecipe.requiresRefractoryBlocks())
          .map(PitBurnRecipe::getOutput)
          .collect(Collectors.toList());
      registry.addIngredientInfo(outputList, ItemStack.class, "gui." + ModuleCharcoal.MOD_ID + ".jei.info.burn.pit");
    }
    */

    // --- Pit Kiln
    {
      registry.addRecipeCatalyst(new ItemStack(ModuleBlocks.KILN_PIT), JEIRecipeCategoryUid.PIT_KILN);
      registry.handleRecipes(KilnPitRecipe.class, JEIRecipeWrapperKilnPit::new, JEIRecipeCategoryUid.PIT_KILN);
      List<KilnPitRecipe> recipeList = new ArrayList<>(Registries.KILN_PIT_RECIPE.getValuesCollection());
      registry.addRecipes(recipeList, JEIRecipeCategoryUid.PIT_KILN);
    }

    // --- Brick Kiln
    {
      registry.addRecipeCatalyst(new ItemStack(ModuleBlocks.KILN_BRICK), JEIRecipeCategoryUid.BRICK_KILN);
      registry.handleRecipes(KilnBrickRecipe.class, JEIRecipeWrapperKilnBrick::new, JEIRecipeCategoryUid.BRICK_KILN);
      List<KilnBrickRecipe> recipeList = new ArrayList<>(Registries.KILN_BRICK_RECIPE.getValuesCollection());
      registry.addRecipes(recipeList, JEIRecipeCategoryUid.BRICK_KILN);
    }

    // --- Pit Burn
    {
      registry.addRecipeCatalyst(new ItemStack(Blocks.DIRT), JEIRecipeCategoryUid.PIT_BURN);
      registry.handleRecipes(PitBurnRecipe.class, JEIRecipeWrapperPitBurn::new, JEIRecipeCategoryUid.PIT_BURN);
      List<PitBurnRecipe> recipeList = Registries.BURN_RECIPE.getValuesCollection()
          .stream()
          .filter(burnRecipe -> !burnRecipe.requiresRefractoryBlocks())
          .collect(Collectors.toList());
      registry.addRecipes(recipeList, JEIRecipeCategoryUid.PIT_BURN);
    }

    // --- Refractory Burn
    {
      registry.addRecipeCatalyst(new ItemStack(ModuleBlocks.REFRACTORY_BRICK), JEIRecipeCategoryUid.REFRACTORY_BURN);
      registry.handleRecipes(
          PitBurnRecipe.class,
          JEIRecipeWrapperRefractoryBurn::new,
          JEIRecipeCategoryUid.REFRACTORY_BURN
      );
      List<PitBurnRecipe> recipeList = new ArrayList<>(Registries.BURN_RECIPE.getValuesCollection());
      registry.addRecipes(recipeList, JEIRecipeCategoryUid.REFRACTORY_BURN);
    }
  }
}
