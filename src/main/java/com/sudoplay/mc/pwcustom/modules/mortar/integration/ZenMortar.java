package com.sudoplay.mc.pwcustom.modules.mortar.integration;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseUndoable;
import com.sudoplay.mc.pwcustom.lib.util.CTUtil;
import com.sudoplay.mc.pwcustom.modules.mortar.api.MortarAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import static com.sudoplay.mc.pwcustom.modules.mortar.integration.ZenMortar.NAME;

@ZenClass(NAME)
@ZenRegister
public class ZenMortar {

  public static final String NAME = "com.sudoplay.mc.ctmortar.Mortar";

  @ZenMethod
  public static void addMixingRecipe(IItemStack output, int duration, IIngredient[] inputs) {

    PluginCraftTweaker.LATE_ADDITIONS.add(new AddMixing(
        InputHelper.toStack(output),
        duration,
        CTUtil.toIngredientArray(inputs)
    ));
  }

  private static class AddMixing
      extends BaseUndoable {

    private final ItemStack output;
    private final int duration;
    private final Ingredient[] inputs;

    /* package */ AddMixing(ItemStack output, int duration, Ingredient[] inputs) {

      super(NAME);
      this.output = output;
      this.duration = duration;
      this.inputs = inputs;
    }

    @Override
    public void apply() {

      MortarAPI.RECIPE_REGISTRY.addMixingRecipe(this.output, this.duration, this.inputs);
    }

    @Override
    protected String getRecipeInfo() {

      return LogHelper.getStackDescription(this.output);
    }
  }

  @ZenMethod
  public static void addCrushingRecipe(IItemStack output, int duration, IIngredient input) {

    PluginCraftTweaker.LATE_ADDITIONS.add(new AddCrushing(
        InputHelper.toStack(output),
        duration,
        CTUtil.toIngredient(input)
    ));
  }

  private static class AddCrushing
      extends BaseUndoable {

    private final ItemStack output;
    private final int duration;
    private final Ingredient input;

    /* package */ AddCrushing(ItemStack output, int duration, Ingredient input) {

      super(NAME);
      this.output = output;
      this.duration = duration;
      this.input = input;
    }

    @Override
    public void apply() {

      MortarAPI.RECIPE_REGISTRY.addCrushingRecipe(this.output, this.duration, this.input);
    }

    @Override
    protected String getRecipeInfo() {

      return LogHelper.getStackDescription(this.output);
    }
  }

}
