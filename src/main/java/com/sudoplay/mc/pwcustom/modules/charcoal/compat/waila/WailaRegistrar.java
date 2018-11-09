package com.sudoplay.mc.pwcustom.modules.charcoal.compat.waila;

import com.sudoplay.mc.pwcustom.library.util.Util;
import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.*;
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

    KilnPitDataProvider pitKilnDataProvider = new KilnPitDataProvider();
    registrar.registerBodyProvider(pitKilnDataProvider, TileKilnPit.class);

    KilnBrickDataProvider kilnBrickDataProvider = new KilnBrickDataProvider();
    registrar.registerBodyProvider(kilnBrickDataProvider, TileKilnBrick.class);
    registrar.registerBodyProvider(kilnBrickDataProvider, TileKilnBrickTop.class);

    CampfireDataProvider campfireDataProvider = new CampfireDataProvider();
    registrar.registerBodyProvider(campfireDataProvider, TileCampfire.class);
  }

}
