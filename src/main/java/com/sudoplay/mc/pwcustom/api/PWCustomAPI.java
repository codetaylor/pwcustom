package com.sudoplay.mc.pwcustom.api;

import com.sudoplay.mc.pwcustom.recipe.RegistryRecipeSawing;
import com.sudoplay.mc.pwcustom.recipe.RegistryRecipeWorkbench;
import com.sudoplay.mc.pwcustom.workbench.block.BlockWorkbench;

import java.util.HashMap;
import java.util.Map;

public class PWCustomAPI {

  public static final class Recipes {

    public static final class Sawing {

      public static final RegistryRecipeSawing REGISTRY = new RegistryRecipeSawing();
    }

    public static final class Workbench {

      public static final Map<String, RegistryRecipeWorkbench> REGISTRY_MAP = new HashMap<>();

      static {

        BlockWorkbench.EnumType[] values = BlockWorkbench.EnumType.values();

        for (BlockWorkbench.EnumType type : values) {
          REGISTRY_MAP.put(type.getName(), new RegistryRecipeWorkbench());
        }
      }
    }
  }

}
