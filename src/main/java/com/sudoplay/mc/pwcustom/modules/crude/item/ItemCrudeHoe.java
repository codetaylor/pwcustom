package com.sudoplay.mc.pwcustom.modules.crude.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class ItemCrudeHoe
    extends ItemHoe {

  public ItemCrudeHoe() {

    super(ToolMaterial.STONE);
    this.setMaxDamage(ToolMaterial.STONE.getMaxUses() / 4);
  }

  @Override
  public float getDestroySpeed(ItemStack stack, IBlockState state) {

    return super.getDestroySpeed(stack, state) * 0.5f;
  }
}
