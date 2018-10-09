package com.sudoplay.mc.pwcustom.modules.charcoal.item;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoalConfig;
import net.minecraft.item.ItemStack;

public class ItemFlintAndTinder
    extends ItemIgniterBase {

  public static final String NAME = "flint_and_tinder";

  public ItemFlintAndTinder() {

    this.setMaxDamage(ModuleCharcoalConfig.GENERAL.FLINT_AND_TINDER_DURABILITY);
    this.setMaxStackSize(1);
  }

  @Override
  public int getMaxItemUseDuration(ItemStack stack) {

    return ModuleCharcoalConfig.GENERAL.FLINT_AND_TINDER_USE_DURATION;
  }

}
