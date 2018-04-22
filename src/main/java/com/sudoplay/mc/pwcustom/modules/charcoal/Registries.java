package com.sudoplay.mc.pwcustom.modules.charcoal;

import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.KilnRecipe;
import com.sudoplay.mc.pwcustom.util.BlockMetaMatcher;

import java.util.ArrayList;
import java.util.List;

public class Registries {

  public static final List<KilnRecipe> KILN_RECIPE_LIST;
  public static final List<BlockMetaMatcher> REFRACTORY_BLOCK_LIST;
  public static final List<BlockMetaMatcher> COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST;

  static {
    KILN_RECIPE_LIST = new ArrayList<>();
    REFRACTORY_BLOCK_LIST = new ArrayList<>();
    COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST = new ArrayList<>();
  }

  private Registries() {
    //
  }

}
