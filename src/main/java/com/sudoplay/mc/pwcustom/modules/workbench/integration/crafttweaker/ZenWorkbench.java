package com.sudoplay.mc.pwcustom.modules.workbench.integration.crafttweaker;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseUndoable;
import com.sudoplay.mc.pwcustom.lib.util.CTUtil;
import com.sudoplay.mc.pwcustom.modules.workbench.api.WorkbenchAPI;
import com.sudoplay.mc.pwcustom.modules.workbench.block.BlockWorkbench;
import com.sudoplay.mc.pwcustom.modules.workbench.recipe.RegistryRecipeWorkbench;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;

@ZenClass("mods.pwcustom.Workbench")
@ZenRegister
public class ZenWorkbench {

  // --------------------------------------------------------------------------
  // - Shaped
  // --------------------------------------------------------------------------

  @ZenMethod
  public static void addRecipeShaped(String table, IItemStack result, IItemStack tool, IIngredient[][] input) {

    ZenWorkbench.addRecipeShaped(table, result, tool, 0, false, input);
  }

  @ZenMethod
  public static void addRecipeShaped(
      String table,
      IItemStack result,
      IIngredient tool,
      int toolDamage,
      IIngredient[][] input
  ) {

    ZenWorkbench.addRecipeShaped(table, result, tool, toolDamage, false, input);
  }

  @ZenMethod
  public static void addRecipeShaped(
      String table,
      IItemStack result,
      IIngredient tool,
      int toolDamage,
      boolean mirrored,
      IIngredient[][] input
  ) {

    PluginCraftTweaker.LATE_ADDITIONS.add(new AddShaped(
        table,
        InputHelper.toStack(result),
        CTUtil.toIngredient(tool),
        CTUtil.toIngredientMatrix(input),
        toolDamage,
        mirrored
    ));
  }

  private static class AddShaped
      extends BaseUndoable {

    private String table;
    private final ItemStack result;
    private final Ingredient tool;
    private final Ingredient[][] input;
    private final int toolDamage;
    private final boolean mirrored;

    AddShaped(String table, ItemStack result, Ingredient tool, Ingredient[][] input, int toolDamage, boolean mirrored) {

      super("WorkbenchShaped");
      this.table = table;
      this.result = result;
      this.tool = tool;
      this.input = input;
      this.toolDamage = toolDamage;
      this.mirrored = mirrored;
    }

    @Override
    public void apply() {

      RegistryRecipeWorkbench registry = WorkbenchAPI.RECIPE_REGISTRY_MAP.get(this.table);

      if (registry != null) {
        registry.addRecipeShaped(this.result, this.tool, this.input, this.toolDamage, this.mirrored);

      } else {
        LogHelper.logError("Unrecognized table name: " + this.table + ", valid values are: " + Arrays.toString(
            BlockWorkbench.EnumType.values()));
      }
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
  public static void addRecipeShapeless(String table, IItemStack result, IItemStack tool, IIngredient[] input) {

    ZenWorkbench.addRecipeShapeless(table, result, tool, 0, input);
  }

  @ZenMethod
  public static void addRecipeShapeless(
      String table,
      IItemStack result,
      IIngredient tool,
      int toolDamage,
      IIngredient[] input
  ) {

    PluginCraftTweaker.LATE_ADDITIONS.add(new AddShapeless(
        table,
        InputHelper.toStack(result),
        CTUtil.toIngredient(tool),
        CTUtil.toIngredientArray(input),
        toolDamage
    ));
  }

  private static class AddShapeless
      extends BaseUndoable {

    private String table;
    private final ItemStack result;
    private final Ingredient tool;
    private final Ingredient[] input;
    private final int toolDamage;

    AddShapeless(String table, ItemStack result, Ingredient tool, Ingredient[] input, int toolDamage) {

      super("WorkbenchShapeless");
      this.table = table;
      this.result = result;
      this.tool = tool;
      this.input = input;
      this.toolDamage = toolDamage;
    }

    @Override
    public void apply() {

      RegistryRecipeWorkbench registry = WorkbenchAPI.RECIPE_REGISTRY_MAP.get(this.table);

      if (registry != null) {
        registry.addRecipeShapeless(this.result, this.tool, this.input, this.toolDamage);

      } else {
        LogHelper.logError("Unrecognized table name: " + this.table + ", valid values are: " + Arrays.toString(
            BlockWorkbench.EnumType.values()));
      }
    }

    @Override
    protected String getRecipeInfo() {

      return LogHelper.getStackDescription(this.result);
    }
  }
}
