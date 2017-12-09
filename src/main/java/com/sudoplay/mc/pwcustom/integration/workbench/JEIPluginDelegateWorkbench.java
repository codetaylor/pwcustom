package com.sudoplay.mc.pwcustom.integration.workbench;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.api.PWCustomAPI;
import com.sudoplay.mc.pwcustom.init.ModBlocks;
import com.sudoplay.mc.pwcustom.recipe.IRecipeWorkbench;
import com.sudoplay.mc.pwcustom.recipe.RecipeWorkbenchShaped;
import com.sudoplay.mc.pwcustom.recipe.RecipeWorkbenchShapeless;
import com.sudoplay.mc.pwcustom.recipe.RegistryRecipeWorkbench;
import com.sudoplay.mc.pwcustom.workbench.block.BlockWorkbench;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class JEIPluginDelegateWorkbench
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
      registry.addRecipeCatalyst(new ItemStack(ModBlocks.WORKBENCH_BASIC, 1, type.getMeta()), this.createUID(type));
    }

    for (BlockWorkbench.EnumType type : BlockWorkbench.EnumType.values()) {
      registry.handleRecipes(RecipeWorkbenchShaped.class, JEIRecipeWrapperWorkbench::new, this.createUID(type));
      registry.handleRecipes(RecipeWorkbenchShapeless.class, JEIRecipeWrapperWorkbench::new, this.createUID(type));
    }

    // TODO: recipe click area

    // Transfer handlers
    IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
    for (BlockWorkbench.EnumType type : BlockWorkbench.EnumType.values()) {
      String uid = this.createUID(type);
      JEIRecipeTransferInfoWorkbenchBasic recipeTransferInfo = new JEIRecipeTransferInfoWorkbenchBasic(type, uid);
      transferRegistry.addRecipeTransferHandler(new JEIRecipeTransferHandlerWorkbench(recipeTransferInfo), uid);
    }

    for (BlockWorkbench.EnumType type : BlockWorkbench.EnumType.values()) {
      List<IRecipeWorkbench> recipeList = new ArrayList<>();
      RegistryRecipeWorkbench recipeRegistry = PWCustomAPI.Recipes.Workbench.REGISTRY_MAP.get(type.getName());
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
