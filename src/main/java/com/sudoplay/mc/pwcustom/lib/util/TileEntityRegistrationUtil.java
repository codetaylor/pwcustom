package com.sudoplay.mc.pwcustom.lib.util;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistrationUtil {

  @SafeVarargs
  public static void registerTileEntities(Class<? extends TileEntity>... tileEntityClasses) {

    for (Class<? extends TileEntity> tileEntityClass : tileEntityClasses) {
      GameRegistry.registerTileEntity(tileEntityClass, ModPWCustom.MOD_ID + "_" + tileEntityClass.getSimpleName());
    }
  }
}
