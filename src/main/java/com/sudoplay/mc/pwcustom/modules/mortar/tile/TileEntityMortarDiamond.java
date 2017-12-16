package com.sudoplay.mc.pwcustom.modules.mortar.tile;

import com.sudoplay.mc.pwcustom.modules.mortar.ModuleConfig;
import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarType;

public class TileEntityMortarDiamond
    extends TileEntityMortarBase {

  public TileEntityMortarDiamond() {

    super(EnumMortarType.DIAMOND);
  }

  @Override
  public int getMaxDurability() {

    return ModuleConfig.Durability.DIAMOND;
  }
}
