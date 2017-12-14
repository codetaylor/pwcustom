package com.sudoplay.mc.pwcustom.modules.workbench.api;

import com.sudoplay.mc.pwcustom.modules.workbench.block.BlockWorkbench;
import com.sudoplay.mc.pwcustom.modules.workbench.recipe.RegistryRecipeWorkbench;

import java.util.HashMap;
import java.util.Map;

public class WorkbenchAPI {

  public static final Map<String, RegistryRecipeWorkbench> RECIPE_REGISTRY_MAP = new HashMap<>();

  static {

    BlockWorkbench.EnumType[] values = BlockWorkbench.EnumType.values();

    for (BlockWorkbench.EnumType type : values) {
      RECIPE_REGISTRY_MAP.put(type.getName(), new RegistryRecipeWorkbench());
    }
  }

  private WorkbenchAPI() {
    //
  }

}
