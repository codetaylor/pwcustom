package com.sudoplay.mc.pwcustom.modules.utils;

import com.codetaylor.mc.athenaeum.integration.crafttweaker.mtlib.helpers.CTLogHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.mc1120.oredict.MCOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.pwcustom.OreDictUtil")
public class ZenOreDictUtil {

  public static final List<IAction> REMOVE_ITEM_LIST;

  static {
    REMOVE_ITEM_LIST = new ArrayList<>();
  }

  public static void apply() {

    for (IAction action : REMOVE_ITEM_LIST) {
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

    if (item == null) {
      CTLogHelper.logError("Can't remove null from ore dict: " + oreDictEntry.getName());
      return;
    }

    REMOVE_ITEM_LIST.add(new ActionOreDictRemoveItemDelayed(oreDictEntry.getName(), item));
  }

  public static class ActionOreDictRemoveItemDelayed
      implements IAction {

    private final String id;
    private final IItemStack item;

    public ActionOreDictRemoveItemDelayed(String id, IItemStack item) {

      this.id = id;
      this.item = item;
    }

    @Override
    public void apply() {

      ItemStack result = ItemStack.EMPTY;

      for (ItemStack itemStack : OreDictionary.getOres(this.id)) {

        if (this.item.matches(CraftTweakerMC.getIItemStackWildcardSize(itemStack))) {
          result = itemStack;
          break;
        }
      }

      if (!result.isEmpty()) {
        int oreId = OreDictionary.getOreID(this.id);
        MCOreDictEntry.getOredictContents().get(oreId).remove(result);
      }
    }

    @Override
    public String describe() {

      return "Removing " + this.item.getDisplayName() + " from ore dictionary entry " + this.id;
    }
  }

}
