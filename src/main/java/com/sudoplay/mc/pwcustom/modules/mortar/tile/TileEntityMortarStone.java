package com.sudoplay.mc.pwcustom.modules.mortar.tile;

import com.sudoplay.mc.pwcustom.modules.mortar.ModuleConfig;
import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarType;

public class TileEntityMortarStone
    extends TileEntityMortarBase {

  public TileEntityMortarStone() {

    super(EnumMortarType.STONE);
  }

  @Override
  public int getMaxDurability() {

    return ModuleConfig.Durability.STONE;
  }
}
