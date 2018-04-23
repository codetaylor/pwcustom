package com.sudoplay.mc.pwcustom.modules.charcoal;

import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.KilnRecipe;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.BurnRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Registries {

  public static final IForgeRegistry<KilnRecipe> KILN_RECIPE;
  public static final IForgeRegistry<BurnRecipe> BURN_RECIPE;

  public static final List<Predicate<IBlockState>> REFRACTORY_BLOCK_LIST;
  public static final List<Predicate<IBlockState>> COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST;

  static {
    KILN_RECIPE = GameRegistry.findRegistry(KilnRecipe.class);
    BURN_RECIPE = GameRegistry.findRegistry(BurnRecipe.class);

    REFRACTORY_BLOCK_LIST = new ArrayList<>();
    COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST = new ArrayList<>();
  }

  private Registries() {
    //
  }

}
