package com.sudoplay.mc.pwcustom.modules.visibility.compat.crafttweaker;

import com.sudoplay.mc.pwcustom.modules.visibility.compat.jei.PluginJEI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.CraftTweaker;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.Collection;

@ZenClass("mods.JEITweaks")
public class ZenJEITweaks {

  @ZenMethod
  public static void addItemsAtRuntime(IItemStack[] ingredients) {

    CraftTweaker.LATE_ACTIONS.add(new ActionAddItemsAtRuntime(ingredients));
  }

  public static class ActionAddItemsAtRuntime
      implements IAction {

    private final IItemStack[] ingredients;

    public ActionAddItemsAtRuntime(IItemStack[] ingredients) {

      this.ingredients = ingredients;
    }

    @Override
    public void apply() {

      PluginJEI.ingredientRegistry.addIngredientsAtRuntime(
          ItemStack.class,
          (Collection<ItemStack>) Arrays.asList(CraftTweakerMC.getItemStacks(this.ingredients))
      );
    }

    @Override
    public String describe() {

      StringBuilder sb = new StringBuilder("Adding ingredients at runtime: [");

      for (int i = 0; i < this.ingredients.length; i++) {
        sb.append(this.ingredients[i].toCommandString());

        if (i < this.ingredients.length - 1) {
          sb.append(", ");
        }
      }
      sb.append("]");

      return sb.toString();
    }
  }
}
