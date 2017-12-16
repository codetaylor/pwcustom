package com.sudoplay.mc.pwcustom.modules.mortar.tile;

import com.sudoplay.mc.pwcustom.modules.mortar.ModuleConfig;
import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarType;

public class TileEntityMortarWood
    extends TileEntityMortarBase {

  public TileEntityMortarWood() {

    super(EnumMortarType.WOOD);
  }

  @Override
  public int getMaxDurability() {

    return ModuleConfig.Durability.WOOD;
  }
}
