package com.sudoplay.mc.pwcustom.modules.visibility;

import net.minecraftforge.common.config.Config;

@Config(modid = ModuleVisibility.MOD_ID, name = ModuleVisibility.MOD_ID + "/" + ModuleVisibility.MOD_ID + ".module.Visibility")
public class ModuleVisibilityConfig {

  @Config.Comment({
      "If true, an attempt will be made to remove JEI items that don't have visible recipes"
  })
  public static boolean ENABLE_JEI_VISIBILITY_CULLING = true;

}
