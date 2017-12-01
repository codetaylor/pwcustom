package com.sudoplay.mc.pwcustom.integration.crafttweaker;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseUndoable;
import com.sudoplay.mc.pwcustom.api.PWCustomAPI;
import com.sudoplay.mc.pwcustom.integration.CraftTweakerPlugin;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.pwcustom.Sawing")
@ZenRegister
public class ZenSawing {

  @ZenMethod
  public static void addRecipe(IItemStack[] drops, IItemStack saw, IItemStack block) {

    CraftTweakerPlugin.LATE_ADDITIONS.add(new Add(
        InputHelper.toStacks(drops),
        InputHelper.toStack(saw),
        InputHelper.toStack(block)
    ));
  }

  private static class Add
      extends BaseUndoable {

    private final ItemStack[] drops;
    private final ItemStack saw;
    private final ItemStack block;

    Add(ItemStack[] drops, ItemStack saw, ItemStack block) {

      super("Sawing");
      this.saw = saw;
      this.block = block;
      this.drops = drops;
    }

    @Override
    public void apply() {

      PWCustomAPI.addSawRecipe(this.drops, this.saw, this.block);
    }

    @Override
    protected String getRecipeInfo() {

      return LogHelper.getStackDescription(this.drops);
    }
  }

}
