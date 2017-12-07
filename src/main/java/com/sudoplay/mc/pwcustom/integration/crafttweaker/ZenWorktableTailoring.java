package com.sudoplay.mc.pwcustom.integration.crafttweaker;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseUndoable;
import com.sudoplay.mc.pwcustom.api.PWCustomAPI;
import com.sudoplay.mc.pwcustom.integration.CraftTweakerPlugin;
import com.sudoplay.mc.pwcustom.util.CTUtil;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.pwcustom.worktable.Tailoring")
@ZenRegister
public class ZenWorktableTailoring {

  // --------------------------------------------------------------------------
  // - Shaped
  // --------------------------------------------------------------------------

  @ZenMethod
  public static void addRecipeShaped(IItemStack result, IItemStack tool, IIngredient[][] input) {

    ZenWorktableTailoring.addRecipeShaped(result, tool, 0, false, input);
  }

  @ZenMethod
  public static void addRecipeShaped(IItemStack result, IItemStack tool, int toolDamage, IIngredient[][] input) {

    ZenWorktableTailoring.addRecipeShaped(result, tool, toolDamage, false, input);
  }

  @ZenMethod
  public static void addRecipeShaped(
      IItemStack result,
      IItemStack tool,
      int toolDamage,
      boolean mirrored,
      IIngredient[][] input
  ) {

    CraftTweakerPlugin.LATE_ADDITIONS.add(new AddShaped(
        InputHelper.toStack(result),
        InputHelper.toStack(tool),
        CTUtil.toIngredientMatrix(input),
        toolDamage,
        mirrored
    ));
  }

  private static class AddShaped
      extends BaseUndoable {

    private final ItemStack result;
    private final ItemStack tool;
    private final Ingredient[][] input;
    private final int toolDamage;
    private final boolean mirrored;

    AddShaped(ItemStack result, ItemStack tool, Ingredient[][] input, int toolDamage, boolean mirrored) {

      super("WorktableTailoringShaped");
      this.result = result;
      this.tool = tool;
      this.input = input;
      this.toolDamage = toolDamage;
      this.mirrored = mirrored;
    }

    @Override
    public void apply() {

      PWCustomAPI.getRegistryRecipeWorkbenchLeatherworking()
          .addRecipeShaped(this.result, this.tool, this.input, this.toolDamage, this.mirrored);
    }

    @Override
    protected String getRecipeInfo() {

      return LogHelper.getStackDescription(this.result);
    }
  }

  // --------------------------------------------------------------------------
  // - Shapeless
  // --------------------------------------------------------------------------

  @ZenMethod
  public static void addRecipeShapeless(IItemStack result, IItemStack tool, IIngredient[] input) {

    ZenWorktableTailoring.addRecipeShapeless(result, tool, 0, input);
  }

  @ZenMethod
  public static void addRecipeShapeless(
      IItemStack result,
      IItemStack tool,
      int toolDamage,
      IIngredient[] input
  ) {

    CraftTweakerPlugin.LATE_ADDITIONS.add(new AddShapeless(
        InputHelper.toStack(result),
        InputHelper.toStack(tool),
        CTUtil.toIngredientArray(input),
        toolDamage
    ));
  }

  private static class AddShapeless
      extends BaseUndoable {

    private final ItemStack result;
    private final ItemStack tool;
    private final Ingredient[] input;
    private final int toolDamage;

    AddShapeless(ItemStack result, ItemStack tool, Ingredient[] input, int toolDamage) {

      super("WorktableTailoringShapeless");
      this.result = result;
      this.tool = tool;
      this.input = input;
      this.toolDamage = toolDamage;
    }

    @Override
    public void apply() {

      PWCustomAPI.getRegistryRecipeWorkbenchLeatherworking()
          .addRecipeShapeless(this.result, this.tool, this.input, this.toolDamage);
    }

    @Override
    protected String getRecipeInfo() {

      return LogHelper.getStackDescription(this.result);
    }
  }
}
