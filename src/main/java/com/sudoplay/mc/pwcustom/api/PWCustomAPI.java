package com.sudoplay.mc.pwcustom.api;

import com.sudoplay.mc.pwcustom.recipe.RegistryRecipeSawing;
import com.sudoplay.mc.pwcustom.recipe.RegistryRecipeWorkbenchBasic;
import com.sudoplay.mc.pwcustom.workbench.block.BlockWorkbenchBasic;

import java.util.HashMap;
import java.util.Map;

public class PWCustomAPI {

  public static final class Recipes {

    public static final class Sawing {

      public static final RegistryRecipeSawing REGISTRY = new RegistryRecipeSawing();
    }

    public static final class Workbench {

      public static final Map<String, RegistryRecipeWorkbenchBasic> REGISTRY_MAP = new HashMap<>();

      static {

        BlockWorkbenchBasic.EnumType[] values = BlockWorkbenchBasic.EnumType.values();

        for (BlockWorkbenchBasic.EnumType type : values) {
          REGISTRY_MAP.put(type.getName(), new RegistryRecipeWorkbenchBasic());
        }
      }
    }
  }

}
