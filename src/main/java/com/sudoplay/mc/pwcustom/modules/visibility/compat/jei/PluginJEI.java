package com.sudoplay.mc.pwcustom.modules.visibility.compat.jei;

import com.sudoplay.mc.pwcustom.modules.visibility.ModuleVisibility;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.plugins.jei.info.IngredientInfoRecipe;
import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.gamestages.data.IStageData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PluginJEI
    implements IModPlugin {

  public static IIngredientBlacklist blacklist;
  public static IRecipeRegistry recipeRegistry;
  public static IIngredientRegistry ingredientRegistry;
  public static IIngredientHelper<ItemStack> ingredientHelper;

  @Override
  public void register(IModRegistry registry) {

    blacklist = registry.getJeiHelpers().getIngredientBlacklist();
    ingredientRegistry = registry.getIngredientRegistry();
    ingredientHelper = ingredientRegistry.getIngredientHelper(ItemStack.class);
  }

  @Override
  public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    recipeRegistry = jeiRuntime.getRecipeRegistry();
  }

  @SideOnly(Side.CLIENT)
  public static void syncHiddenItems(EntityPlayer player) {

    if (player != null && player.getEntityWorld().isRemote) {

      // JEI only allows blacklisting from the main client thread.
      if (!Minecraft.getMinecraft().isCallingFromMinecraftThread()) {

        // Reschedules the sync to the correct thread.
        Minecraft.getMinecraft().addScheduledTask(() -> syncHiddenItems(player));
        return;
      }

      ModuleVisibility.LOGGER.info("Syncing {} items with JEI!.", ModuleVisibility.VISIBILITY_STAGES.size());
      final long time = System.currentTimeMillis();

      final Collection<ItemStack> itemBlacklist = new ArrayList<>();
      final Collection<ItemStack> itemWhitelist = new ArrayList<>();
      final Collection<FluidStack> fluidBlacklist = new ArrayList<>();
      final Collection<FluidStack> fluidWhitelist = new ArrayList<>();

      // Gets the client player's stage data
      final IStageData stageData = GameStageHelper.getPlayerData(player);

      // Loops through all the known stages
      for (final String key : ModuleVisibility.SORTED_STAGES.keySet()) {

        // Gets all items staged to the current stage.
        final List<ItemStack> entries = ModuleVisibility.SORTED_STAGES.get(key);

        // If player has the stage, it is whitelisted.
        if (stageData.hasStage(key)) {

          itemWhitelist.addAll(ingredientHelper.expandSubtypes(entries));
        }

        // If player doesn't have the stage, it is blacklisted.
        else {

          itemBlacklist.addAll(ingredientHelper.expandSubtypes(entries));
        }
      }

      for (final String key : ModuleVisibility.FLUID_STAGES.keySet()) {

        if (stageData.hasStage(key)) {

          fluidWhitelist.addAll(ModuleVisibility.FLUID_STAGES.get(key));
        } else {

          fluidBlacklist.addAll(ModuleVisibility.FLUID_STAGES.get(key));
        }
      }

      if (!itemBlacklist.isEmpty()) {

        ingredientRegistry.removeIngredientsAtRuntime(ItemStack.class, itemBlacklist);
      }

      if (!itemWhitelist.isEmpty()) {

        ingredientRegistry.addIngredientsAtRuntime(ItemStack.class, itemWhitelist);
      }

      if (!fluidBlacklist.isEmpty()) {

        ingredientRegistry.removeIngredientsAtRuntime(FluidStack.class, fluidBlacklist);
      }

      if (!fluidWhitelist.isEmpty()) {

        ingredientRegistry.addIngredientsAtRuntime(FluidStack.class, fluidWhitelist);
      }

      ModuleVisibility.LOGGER.info("Finished JEI Sync, took "
          + (System.currentTimeMillis() - time) + "ms. " + itemBlacklist
          .size() + " hidden, " + itemWhitelist.size() + " shown.");
    }

    processRecipes();
  }

  @SideOnly(Side.CLIENT)
  public static void processRecipes() {

    if (!FMLCommonHandler.instance().getEffectiveSide().isClient()) {
      return;
    }

    if (PluginJEI.recipeRegistry == null
        || PluginJEI.ingredientRegistry == null) {
      return;
    }

    Collection<ItemStack> ingredients = PluginJEI.ingredientRegistry.getAllIngredients(ItemStack.class);
    final Collection<ItemStack> itemBlacklist = new ArrayList<>();
    final Collection<ItemStack> itemWhitelist = new ArrayList<>();

    for (ItemStack ingredient : ingredients) {
      IFocus<ItemStack> focus = PluginJEI.recipeRegistry.createFocus(IFocus.Mode.OUTPUT, ingredient);
      List<IRecipeCategory> recipeCategories = PluginJEI.recipeRegistry.getRecipeCategories(focus);
      boolean isWhitelisted = false;

      if (!recipeCategories.isEmpty()) {

        categories:
        for (IRecipeCategory recipeCategory : recipeCategories) {
          List<Object> recipeCatalysts = PluginJEI.recipeRegistry.getRecipeCatalysts(recipeCategory);

          if (!recipeCatalysts.isEmpty()) {

            //noinspection unchecked
            List recipeWrappers = PluginJEI.recipeRegistry.getRecipeWrappers(recipeCategory, focus);

            for (Object recipeWrapper : recipeWrappers) {

              if (!(recipeWrapper instanceof IngredientInfoRecipe)) {
                itemWhitelist.add(ingredient);
                isWhitelisted = true;
                break categories;
              }
            }
          }
        }
      }

      if (!isWhitelisted) {
        itemBlacklist.add(ingredient);
      }
    }

    if (!itemBlacklist.isEmpty()) {
      PluginJEI.ingredientRegistry.removeIngredientsAtRuntime(ItemStack.class, itemBlacklist);
    }

    if (!itemWhitelist.isEmpty()) {
      PluginJEI.ingredientRegistry.addIngredientsAtRuntime(ItemStack.class, itemWhitelist);
    }
  }
}
