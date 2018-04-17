package com.sudoplay.mc.pwcustom.modules.utils;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.mc1120.actions.ActionOreDictRemoveItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.pwcustom.OreDictUtil")
public class ZenOreDictUtil {

  public static final List<ActionOreDictRemoveItem> REMOVE_ITEM_LIST;

  static {
    REMOVE_ITEM_LIST = new ArrayList<>();
  }

  public static void apply() {

    for (ActionOreDictRemoveItem action : REMOVE_ITEM_LIST) {
      CraftTweakerAPI.apply(action);
    }
  }

  @ZenMethod
  public static void removeItems(IOreDictEntry oreDictEntry, IItemStack[] items) {

    for (IItemStack item : items) {
      ZenOreDictUtil.remove(oreDictEntry, item);
    }
  }

  @ZenMethod
  public static void remove(IOreDictEntry oreDictEntry, IItemStack item) {

    ItemStack result = ItemStack.EMPTY;

    for (ItemStack itemStack : OreDictionary.getOres(oreDictEntry.getName())) {

      if (item.matches(CraftTweakerMC.getIItemStackWildcardSize(itemStack))) {
        result = itemStack;
        break;
      }
    }

    if (!result.isEmpty()) {
      REMOVE_ITEM_LIST.add(new ActionOreDictRemoveItem(oreDictEntry.getName(), result));
    }
  }

}
