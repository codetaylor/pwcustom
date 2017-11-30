package com.sudoplay.mc.ctpwcustom.integration.crafttweaker;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseUndoable;
import com.sudoplay.mc.ctpwcustom.api.PWCustomAPI;
import com.sudoplay.mc.ctpwcustom.integration.CraftTweakerPlugin;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.ctpwcustom.Sawing")
@ZenRegister
public class Sawing {

  @ZenMethod
  public static void addSawingRecipe(IItemStack[] drops, IItemStack saw, IItemStack block) {

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

    protected Add(ItemStack[] drops, ItemStack saw, ItemStack block) {

      super("Sawing");
      this.saw = saw;
      this.block = block;
      this.drops = drops;
    }

    @Override
    public void apply() {

      PWCustomAPI.addSawRecipe(this.saw, this.block, this.drops);
    }

    @Override
    protected String getRecipeInfo() {

      return LogHelper.getStackDescription(this.drops);
    }
  }

}
