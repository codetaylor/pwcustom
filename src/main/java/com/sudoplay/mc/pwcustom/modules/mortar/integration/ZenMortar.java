package com.sudoplay.mc.pwcustom.modules.mortar.integration;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseUndoable;
import com.sudoplay.mc.pwcustom.integration.PluginCraftTweaker;
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
  public static void addRecipe(IItemStack output, IIngredient[] inputs) {

    PluginCraftTweaker.LATE_ADDITIONS.add(new Add(
        InputHelper.toStack(output),
        CTUtil.toIngredientArray(inputs)
    ));
  }

  private static class Add
      extends BaseUndoable {

    private final ItemStack output;
    private final Ingredient[] inputs;

    /* package */ Add(ItemStack output, Ingredient[] inputs) {

      super(NAME);
      this.output = output;
      this.inputs = inputs;
    }

    @Override
    public void apply() {

      MortarAPI.RECIPE_REGISTRY.addRecipe(this.output, this.inputs);
    }

    @Override
    protected String getRecipeInfo() {

      return LogHelper.getStackDescription(this.output);
    }
  }

}
