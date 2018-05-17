package com.sudoplay.mc.pwcustom.modules.visibility.compat.crafttweaker;

import com.sudoplay.mc.pwcustom.modules.visibility.compat.jei.PluginJEI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mezz.jei.api.ingredients.IIngredientRegistry;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.Collection;

@ZenClass("mods.JEITweaks")
public class ZenJEITweaks {

  @ZenMethod
  public static void addItemsAtRuntime(IItemStack[] ingredients) {

    PluginJEI.INGREDIENT_REGISTRATION_ACTIONS.add(new ActionAddItemsAtRuntime(ingredients));
  }

  public static class ActionAddItemsAtRuntime
      implements PluginJEI.IIngredientRegistrationAction {

    private final IItemStack[] ingredients;

    public ActionAddItemsAtRuntime(IItemStack[] ingredients) {

      this.ingredients = ingredients;
    }

    @Override
    public void apply(IIngredientRegistry registry) {

      registry.addIngredientsAtRuntime(
          ItemStack.class,
          (Collection<ItemStack>) Arrays.asList(CraftTweakerMC.getItemStacks(this.ingredients))
      );
    }
  }
}
