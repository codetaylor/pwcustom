package com.sudoplay.mc.pwcustom.modules.charcoal;

import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.PitKilnRecipe;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.PitBurnRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Registries {

  public static final IForgeRegistry<PitKilnRecipe> KILN_RECIPE;
  public static final IForgeRegistry<PitBurnRecipe> BURN_RECIPE;

  public static final List<Predicate<IBlockState>> REFRACTORY_BLOCK_LIST;
  public static final List<Predicate<IBlockState>> COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST;

  static {
    KILN_RECIPE = GameRegistry.findRegistry(PitKilnRecipe.class);
    BURN_RECIPE = GameRegistry.findRegistry(PitBurnRecipe.class);

    REFRACTORY_BLOCK_LIST = new ArrayList<>();
    COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST = new ArrayList<>();
  }

  private Registries() {
    //
  }

}
