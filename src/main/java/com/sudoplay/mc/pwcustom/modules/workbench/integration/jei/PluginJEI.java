package com.sudoplay.mc.pwcustom.modules.workbench.integration.jei;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.modules.workbench.ModuleWorkbench;
import com.sudoplay.mc.pwcustom.modules.workbench.api.WorkbenchAPI;
import com.sudoplay.mc.pwcustom.modules.workbench.block.BlockWorkbench;
import com.sudoplay.mc.pwcustom.modules.workbench.recipe.IRecipeWorkbench;
import com.sudoplay.mc.pwcustom.modules.workbench.recipe.RecipeWorkbenchShaped;
import com.sudoplay.mc.pwcustom.modules.workbench.recipe.RecipeWorkbenchShapeless;
import com.sudoplay.mc.pwcustom.modules.workbench.recipe.RegistryRecipeWorkbench;
import mezz.jei.api.*;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class PluginJEI
    implements IModPlugin {

  private IJeiHelpers jeiHelpers;

  @Override
  public void register(IModRegistry registry) {

    this.jeiHelpers = registry.getJeiHelpers();

    // Workbench
    for (BlockWorkbench.EnumType type : BlockWorkbench.EnumType.values()) {
      registry.addRecipeCategories(this.createWorkbenchCategory(type));
    }

    for (BlockWorkbench.EnumType type : BlockWorkbench.EnumType.values()) {
      registry.addRecipeCatalyst(
          new ItemStack(ModuleWorkbench.Blocks.WORKBENCH_BASIC, 1, type.getMeta()),
          this.createUID(type)
      );
    }

    for (BlockWorkbench.EnumType type : BlockWorkbench.EnumType.values()) {
      registry.handleRecipes(RecipeWorkbenchShaped.class, JEIRecipeWrapperWorkbench::new, this.createUID(type));
      registry.handleRecipes(RecipeWorkbenchShapeless.class, JEIRecipeWrapperWorkbench::new, this.createUID(type));
    }

    // TODO: recipe click area

    // Transfer handlers
    IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
    for (BlockWorkbench.EnumType type : BlockWorkbench.EnumType.values()) {
      transferRegistry.addRecipeTransferHandler(new JEIRecipeTransferInfoWorkbenchBasic(type, this.createUID(type)));
    }

    for (BlockWorkbench.EnumType type : BlockWorkbench.EnumType.values()) {
      List<IRecipeWorkbench> recipeList = new ArrayList<>();
      RegistryRecipeWorkbench recipeRegistry = WorkbenchAPI.RECIPE_REGISTRY_MAP.get(type.getName());
      recipeRegistry.getRecipeShapedList(recipeList);
      recipeRegistry.getRecipeShapelessList(recipeList);
      registry.addRecipes(recipeList, this.createUID(type));
    }
  }

  private JEICategoryWorkbench createWorkbenchCategory(BlockWorkbench.EnumType type) {

    return new JEICategoryWorkbench(
        this.createUID(type),
        this.createTitleTranslateKey(type),
        this.createBackground(type)
    );
  }

  private IDrawable createBackground(BlockWorkbench.EnumType type) {

    IGuiHelper guiHelper = this.jeiHelpers.getGuiHelper();
    ResourceLocation resourceLocation = new ResourceLocation(
        ModPWCustom.MOD_ID,
        "textures/gui/workbench_basic_" + type.getName() + ".png"
    );
    return guiHelper.createDrawable(resourceLocation, 0 + 3, 0 + 3, 176 - 6, 80);
  }

  private String createTitleTranslateKey(BlockWorkbench.EnumType type) {

    return "text." + ModPWCustom.MOD_ID + ".jei.category.workbench." + type.getName();
  }

  private String createUID(BlockWorkbench.EnumType type) {

    return ModPWCustom.MOD_ID + "_" + type.getName();
  }
}
