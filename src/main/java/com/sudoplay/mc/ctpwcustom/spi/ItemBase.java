package com.sudoplay.mc.ctpwcustom.spi;

import com.sudoplay.mc.ctpwcustom.ModPWCustom;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class ItemBase
    extends Item {

  public ItemBase(String name) {

    this.setRegistryName(new ResourceLocation(ModPWCustom.MOD_ID, name));
    this.setUnlocalizedName(ModPWCustom.MOD_ID + "." + name);
    this.setCreativeTab(ModPWCustom.CREATIVE_TAB);
  }
}
