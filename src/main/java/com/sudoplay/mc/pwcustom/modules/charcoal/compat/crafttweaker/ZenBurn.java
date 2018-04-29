package com.sudoplay.mc.pwcustom.modules.charcoal.compat.crafttweaker;

import com.codetaylor.mc.athenaeum.parser.recipe.item.MalformedRecipeItemException;
import com.codetaylor.mc.athenaeum.parser.recipe.item.RecipeItemParser;
import com.sudoplay.mc.pwcustom.modules.charcoal.Registries;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.PitBurnRecipe;
import com.sudoplay.mc.pwcustom.library.util.BlockMetaMatcher;
import com.sudoplay.mc.pwcustom.library.util.Util;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.CraftTweaker;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.pwcustom.Burn")
public class ZenBurn {

  @ZenMethod
  public static void addRecipe(
      String name,
      IItemStack output,
      String blockString,
      int burnStages,
      int totalBurnTimeTicks,
      ILiquidStack fluidProduced,
      float failureChance,
      IItemStack[] failureItems,
      boolean requiresRefractoryBlocks,
      boolean fluidLevelAffectsFailureChance
  ) {

    CraftTweaker.LATE_ACTIONS.add(new AddRecipe(
        name,
        CraftTweakerMC.getItemStack(output),
        blockString,
        burnStages,
        totalBurnTimeTicks,
        CraftTweakerMC.getLiquidStack(fluidProduced),
        failureChance,
        CraftTweakerMC.getItemStacks(failureItems),
        requiresRefractoryBlocks,
        fluidLevelAffectsFailureChance
    ));
  }

  public static class AddRecipe
      implements IAction {

    private final String name;
    private final String blockString;
    private final ItemStack output;
    private final int burnStages;
    private final int totalBurnTimeTicks;
    private final FluidStack fluidProduced;
    private final float failureChance;
    private final ItemStack[] failureItems;
    private final boolean requiresRefractoryBlocks;
    private final boolean fluidLevelAffectsFailureChance;

    public AddRecipe(
        String name,
        ItemStack output,
        String blockString,
        int burnStages,
        int totalBurnTimeTicks,
        FluidStack fluidProduced,
        float failureChance,
        ItemStack[] failureItems,
        boolean requiresRefractoryBlocks,
        boolean fluidLevelAffectsFailureChance
    ) {

      this.name = name;
      this.output = output;
      this.blockString = blockString;
      this.burnStages = burnStages;
      this.totalBurnTimeTicks = totalBurnTimeTicks;
      this.fluidProduced = fluidProduced;
      this.failureChance = failureChance;
      this.failureItems = failureItems;
      this.requiresRefractoryBlocks = requiresRefractoryBlocks;
      this.fluidLevelAffectsFailureChance = fluidLevelAffectsFailureChance;
    }

    @Override
    public void apply() {

      try {
        BlockMetaMatcher blockMetaMatcher = Util.parseBlockStringWithWildcard(this.blockString, new RecipeItemParser());

        PitBurnRecipe recipe = new PitBurnRecipe(
            this.output,
            blockMetaMatcher,
            this.burnStages,
            this.totalBurnTimeTicks,
            this.fluidProduced,
            this.failureChance,
            this.failureItems,
            this.requiresRefractoryBlocks,
            this.fluidLevelAffectsFailureChance
        );
        Registries.BURN_RECIPE.register(recipe.setRegistryName(new ResourceLocation("crafttweaker", this.name)));

      } catch (MalformedRecipeItemException e) {
        CraftTweakerAPI.logError("", e);
      }
    }

    @Override
    public String describe() {

      return "Adding burn recipe for " + this.output;
    }
  }

}
