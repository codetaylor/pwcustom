package com.sudoplay.mc.pwcustom.modules.visibility.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.liquid.ILiquidStack;
import net.darkhax.bookshelf.util.ModUtils;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.VisibilityStages")
public class ZenVisibilityStages {

  @ZenMethod
  public static void addVisibilityStage(String stage, IIngredient input) {

    CraftTweakerAPI.apply(new ActionAddVisibilityRestriction(stage, input));
  }

  @ZenMethod
  public static void removeVisibilityStage(IIngredient input) {

    CraftTweakerAPI.apply(new ActionRemoveRestriction(input));
  }

  @ZenMethod
  public static void stageModVisibility(String stage, String modid) {

    for (Item item : ModUtils.getSortedEntries(ForgeRegistries.ITEMS).get(modid)) {

      if (item != null && item != Items.AIR) {
        CraftTweakerAPI.apply(new ActionAddVisibilityRestriction(stage, item));
      }
    }
  }

  @ZenMethod
  public static void stageLiquid(String stage, ILiquidStack stack) {

    CraftTweakerAPI.apply(new ActionStageLiquid(stage, stack));
  }

}
