package com.sudoplay.mc.pwcustom.modules.mortar.tile;

import com.sudoplay.mc.pwcustom.modules.mortar.ModuleConfig;
import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarType;

public class TileEntityMortarIron
    extends TileEntityMortarBase {

  public TileEntityMortarIron() {

    super(EnumMortarType.IRON);
  }

  @Override
  public int getMaxDurability() {

    return ModuleConfig.Durability.IRON;
  }
}
