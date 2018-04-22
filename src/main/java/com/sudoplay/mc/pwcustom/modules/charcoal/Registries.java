package com.sudoplay.mc.pwcustom.modules.charcoal;

import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.KilnRecipe;
import net.minecraft.block.state.IBlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Registries {

  public static final List<KilnRecipe> KILN_RECIPE_LIST;
  public static final List<Predicate<IBlockState>> REFRACTORY_BLOCK_LIST;
  public static final List<Predicate<IBlockState>> COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST;

  static {
    KILN_RECIPE_LIST = new ArrayList<>();
    REFRACTORY_BLOCK_LIST = new ArrayList<>();
    COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST = new ArrayList<>();
  }

  private Registries() {
    //
  }

}
