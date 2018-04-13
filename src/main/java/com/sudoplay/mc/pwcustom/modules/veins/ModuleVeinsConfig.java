package com.sudoplay.mc.pwcustom.modules.veins;

import net.minecraftforge.common.config.Config;

@Config(modid = ModuleVeins.MOD_ID, name = ModuleVeins.MOD_ID + ".module.Veins")
public class ModuleVeinsConfig {

  @Config.Comment({
      "Chance to spawn fake surface indicator if vein is not spawned"
  })
  public static float FAKE_SURFACE_INDICATOR_CHANCE = 0.125f;

  @Config.Comment({
      "If all criteria are met, chance vein will spawn"
  })
  public static float VEIN_CHANCE = 0.5f;

}
