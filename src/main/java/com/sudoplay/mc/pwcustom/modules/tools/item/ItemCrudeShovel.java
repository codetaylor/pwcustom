package com.sudoplay.mc.pwcustom.modules.tools.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemCrudeShovel
    extends ItemSpade {

  public ItemCrudeShovel() {

    super(ToolMaterial.STONE);
    this.setMaxDamage(ToolMaterial.STONE.getMaxUses() / 4);
  }

  @Override
  public float getDestroySpeed(ItemStack stack, IBlockState state) {

    return super.getDestroySpeed(stack, state) * 0.5f;
  }
}
