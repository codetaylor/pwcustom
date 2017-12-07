package com.sudoplay.mc.pwcustom.api;

import com.sudoplay.mc.pwcustom.recipe.RegistryRecipeSawing;
import com.sudoplay.mc.pwcustom.recipe.RegistryRecipeWorkbench;

public class PWCustomAPI {

  private static final RegistryRecipeSawing REGISTRY_RECIPE_SAWING;
  private static final RegistryRecipeWorkbench REGISTRY_RECIPE_WORKBENCH_LEATHERWORKING;

  static {
    REGISTRY_RECIPE_SAWING = new RegistryRecipeSawing();
    REGISTRY_RECIPE_WORKBENCH_LEATHERWORKING = new RegistryRecipeWorkbench();
  }

  // --------------------------------------------------------------------------
  // - Sawing
  // --------------------------------------------------------------------------

  public static RegistryRecipeSawing getRegistryRecipeSawing() {

    return REGISTRY_RECIPE_SAWING;
  }

  // --------------------------------------------------------------------------
  // - Workbench Leatherworking
  // --------------------------------------------------------------------------

  public static RegistryRecipeWorkbench getRegistryRecipeWorkbenchLeatherworking() {

    return REGISTRY_RECIPE_WORKBENCH_LEATHERWORKING;
  }

}
