package com.sudoplay.mc.pwcustom.modules.charcoal.recipe;

import com.sudoplay.mc.pwcustom.modules.charcoal.Registries;
import com.sudoplay.mc.pwcustom.util.BlockMetaMatcher;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class BurnRecipe
    extends IForgeRegistryEntry.Impl<BurnRecipe> {

  @Nullable
  public static BurnRecipe getRecipe(IBlockState input) {

    for (BurnRecipe recipe : Registries.BURN_RECIPE) {

      if (recipe.matches(input)) {
        return recipe;
      }
    }

    return null;
  }

  private final BlockMetaMatcher inputMatcher;
  private final ItemStack output;
  private final int burnStages;
  private final int totalBurnTimeTicks;
  private final FluidStack fluidProduced;
  private final float failureChance;
  private final ItemStack[] failureItems;
  private final boolean requiresRefractoryBlocks;
  private final boolean fluidLevelAffectsFailureChance;

  public BurnRecipe(
      ItemStack output,
      BlockMetaMatcher inputMatcher,
      int burnStages,
      int totalBurnTimeTicks,
      FluidStack fluidProduced,
      float failureChance,
      ItemStack[] failureItems,
      boolean requiresRefractoryBlocks,
      boolean fluidLevelAffectsFailureChance
  ) {

    this.inputMatcher = inputMatcher;
    this.output = output;
    this.burnStages = burnStages;
    this.totalBurnTimeTicks = totalBurnTimeTicks;
    this.fluidProduced = fluidProduced;
    this.failureChance = failureChance;
    this.failureItems = failureItems;
    this.requiresRefractoryBlocks = requiresRefractoryBlocks;
    this.fluidLevelAffectsFailureChance = fluidLevelAffectsFailureChance;
  }

  public BlockMetaMatcher getInputMatcher() {

    return this.inputMatcher;
  }

  public ItemStack getOutput() {

    return this.output.copy();
  }

  public int getBurnStages() {

    return this.burnStages;
  }

  public int getTotalBurnTimeTicks() {

    return this.totalBurnTimeTicks;
  }

  @Nullable
  public FluidStack getFluidProduced() {

    if (this.fluidProduced != null) {
      return this.fluidProduced.copy();
    }

    return null;
  }

  public float getFailureChance() {

    return this.failureChance;
  }

  public ItemStack[] getFailureItems() {

    return this.failureItems;
  }

  public boolean requiresRefractoryBlocks() {

    return this.requiresRefractoryBlocks;
  }

  public boolean doesFluidLevelAffectsFailureChance() {

    return this.fluidLevelAffectsFailureChance;
  }

  public boolean matches(IBlockState input) {

    return this.inputMatcher.test(input);
  }
}
