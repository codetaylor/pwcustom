package com.sudoplay.mc.pwcustom.modules.sawing.integration;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseUndoable;
import com.sudoplay.mc.pwcustom.api.PWCustomAPI;
import com.sudoplay.mc.pwcustom.integration.PluginCraftTweaker;
import com.sudoplay.mc.pwcustom.lib.util.CTUtil;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.pwcustom.Sawing")
@ZenRegister
public class ZenSawing {

  @ZenMethod
  public static void addRecipe(IItemStack[] drops, IItemStack saw, IIngredient block) {

    PluginCraftTweaker.LATE_ADDITIONS.add(new Add(
        InputHelper.toStacks(drops),
        InputHelper.toStack(saw),
        CTUtil.toIngredient(block)
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

      PWCustomAPI.Recipes.Sawing.REGISTRY.addRecipe(this.drops, this.saw, this.block);
    }

    @Override
    protected String getRecipeInfo() {

      return LogHelper.getStackDescription(this.drops);
    }
  }

}
