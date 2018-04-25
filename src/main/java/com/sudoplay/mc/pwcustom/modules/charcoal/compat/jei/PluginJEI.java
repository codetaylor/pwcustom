package com.sudoplay.mc.pwcustom.modules.charcoal.compat.jei;

import com.sudoplay.mc.pwcustom.modules.charcoal.Registries;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleBlocks;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.KilnRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PluginJEI
    implements IModPlugin {

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {

    IJeiHelpers jeiHelpers = registry.getJeiHelpers();

    registry.addRecipeCategories(
        new JEIRecipeCategoryPitKiln(jeiHelpers.getGuiHelper())
    );
  }

  @Override
  public void register(IModRegistry registry) {

    // --- Pit Kiln
    registry.addRecipeCatalyst(new ItemStack(ModuleBlocks.KILN), JEIRecipeCategoryUid.PIT_KILN);
    registry.handleRecipes(KilnRecipe.class, JEIRecipeWrapperPitKiln::new, JEIRecipeCategoryUid.PIT_KILN);
    List<KilnRecipe> recipeList = new ArrayList<>(Registries.KILN_RECIPE.getValuesCollection());
    registry.addRecipes(recipeList, JEIRecipeCategoryUid.PIT_KILN);
  }
}
