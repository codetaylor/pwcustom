package com.sudoplay.mc.pwcustom.modules.charcoal.compat.crafttweaker;

import com.sudoplay.mc.pwcustom.modules.charcoal.Registries;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.KilnRecipe;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.CraftTweaker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.pwcustom.PitKiln")
public class ZenPitKiln {

  @ZenMethod
  public static void addRecipe(String name, IItemStack output, IIngredient input, int burnTimeTicks) {

    ZenPitKiln.addRecipe(name, output, input, burnTimeTicks, 0, new IItemStack[0]);
  }

  @ZenMethod
  public static void addRecipe(
      String name,
      IItemStack output,
      IIngredient input,
      int burnTimeTicks,
      float failureChance
  ) {

    ZenPitKiln.addRecipe(name, output, input, burnTimeTicks, failureChance, new IItemStack[0]);
  }

  @ZenMethod
  public static void addRecipe(
      String name,
      IItemStack output,
      IIngredient input,
      int burnTimeTicks,
      float failureChance,
      IItemStack[] failureItems
  ) {

    CraftTweaker.LATE_ACTIONS.add(new AddRecipe(
        name,
        CraftTweakerMC.getItemStack(output),
        CraftTweakerMC.getIngredient(input),
        burnTimeTicks,
        failureChance,
        CraftTweakerMC.getItemStacks(failureItems)
    ));
  }

  public static class AddRecipe
      implements IAction {

    private final ItemStack output;
    private final String name;
    private final Ingredient input;
    private final int burnTimeTicks;
    private final float failureChance;
    private final ItemStack[] failureItems;

    public AddRecipe(
        String name,
        ItemStack output,
        Ingredient input,
        int burnTimeTicks,
        float failureChance,
        ItemStack[] failureItems
    ) {

      this.name = name;
      this.input = input;
      this.output = output;
      this.burnTimeTicks = burnTimeTicks;
      this.failureChance = failureChance;
      this.failureItems = failureItems;
    }

    @Override
    public void apply() {

      KilnRecipe recipe = new KilnRecipe(
          this.input,
          this.output,
          this.burnTimeTicks,
          this.failureChance,
          this.failureItems
      );
      Registries.KILN_RECIPE.register(recipe.setRegistryName(new ResourceLocation("crafttweaker", this.name)));
    }

    @Override
    public String describe() {

      return "Adding pit kiln recipe for " + this.output;
    }
  }

}
