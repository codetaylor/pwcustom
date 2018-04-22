package com.sudoplay.mc.pwcustom.modules.charcoal.compat.waila;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.IProgressProvider;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileTarTankBase;
import com.sudoplay.mc.pwcustom.util.Util;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaRegistrar {

  static final String CONFIG_TANK = Util.prefix("tank");
  static final String CONFIG_PROGRESS = Util.prefix("progress");

  @SuppressWarnings("unused")
  public static void wailaCallback(IWailaRegistrar registrar) {

    registrar.addConfig(ModuleCharcoal.MOD_ID, CONFIG_TANK, true);
    registrar.addConfig(ModuleCharcoal.MOD_ID, CONFIG_PROGRESS, true);

    TankDataProvider tankDataProvider = new TankDataProvider();
    registrar.registerBodyProvider(tankDataProvider, TileTarTankBase.class);

    PitKilnDataProvider pitKilnDataProvider = new PitKilnDataProvider();
    registrar.registerBodyProvider(pitKilnDataProvider, IProgressProvider.class);
  }

}
