package com.sudoplay.mc.pwcustom.modules.utils;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class ModuleUtils
    extends ModuleBase {

  public static final String MOD_ID = ModPWCustom.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModPWCustom.CREATIVE_TAB;

  public ModuleUtils() {

    super(0, MOD_ID);

    this.registerIntegrationPlugin(
        "crafttweaker",
        "com.sudoplay.mc.pwcustom.modules.utils.ZenOreDictUtil"
    );
  }

  @Override
  public void onPostInitializationEvent(FMLPostInitializationEvent event) {

    super.onPostInitializationEvent(event);

    if (Loader.isModLoaded("crafttweaker")) {
      ZenOreDictUtil.apply();
    }
  }
}
