package com.sudoplay.mc.pwcustom.modules.workbench.item;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.BlockRegistrationHelper;
import com.sudoplay.mc.pwcustom.modules.workbench.block.BlockWorkbench;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemWorkbench
    extends ItemMultiTexture {

  public ItemWorkbench(
      BlockWorkbench block
  ) {

    super(block, block, block::getName);
    BlockRegistrationHelper.setRegistryName(block, this);
    this.setCreativeTab(ModPWCustom.CREATIVE_TAB);
  }

  @Override
  public int getItemBurnTime(ItemStack itemStack) {

    return 0;
  }
}
