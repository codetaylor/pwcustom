package com.sudoplay.mc.pwcustom.modules.sawing.integration.crafttweaker;

import com.codetaylor.mc.athenaeum.integration.crafttweaker.PluginDelegate;
import com.codetaylor.mc.athenaeum.integration.crafttweaker.mtlib.helpers.CTInputHelper;
import com.codetaylor.mc.athenaeum.integration.crafttweaker.mtlib.helpers.CTLogHelper;
import com.codetaylor.mc.athenaeum.integration.crafttweaker.mtlib.utils.BaseUndoable;
import com.sudoplay.mc.pwcustom.modules.sawing.api.SawingAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.pwcustom.Sawing")
public class ZenSawing {

  @ZenMethod
  public static void addRecipe(IItemStack[] drops, IItemStack saw, IIngredient block) {

    PluginDelegate.LATE_ADDITIONS.add(new Add(
        CTInputHelper.toStacks(drops),
        CTInputHelper.toStack(saw),
        CTInputHelper.toIngredient(block)
    ));
  }

  private static class Add
      extends BaseUndoable {

    private final ItemStack[] drops;
    private final ItemStack saw;
    private final Ingredient block;

    Add(ItemStack[] drops, ItemStack saw, Ingredient block) {

      super("Sawing");
      this.saw = saw;
      this.block = block;
      this.drops = drops;
    }

    @Override
    public void apply() {

      SawingAPI.RECIPE_REGISTRY.addRecipe(this.drops, this.saw, this.block);
    }

    @Override
    protected String getRecipeInfo() {

      return CTLogHelper.getStackDescription(this.drops);
    }
  }

}
