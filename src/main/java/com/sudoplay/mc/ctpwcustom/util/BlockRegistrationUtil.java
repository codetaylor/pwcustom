package com.sudoplay.mc.ctpwcustom.util;

import com.google.common.base.Preconditions;
import com.sudoplay.mc.ctpwcustom.spi.IBlockColored;
import com.sudoplay.mc.ctpwcustom.spi.IBlockVariant;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.util.ResourceLocation;

public class BlockRegistrationUtil {

  public static ItemBlock[] createItemBlocks(Block... blocks) {

    ItemBlock[] result = new ItemBlock[blocks.length];

    for (int i = 0; i < blocks.length; i++) {
      ItemBlock itemBlock = BlockRegistrationUtil.createItemBlock(blocks[i]);
      result[i] = itemBlock;
    }

    return result;
  }

  public static ItemBlock createItemBlock(Block block) {

    ItemBlock itemBlock;

    if (block instanceof IBlockColored) {
      itemBlock = new ItemColored(block, ((IBlockColored) block).hasBlockColoredSubtypes());

    } else if (block instanceof IBlockVariant) {
      itemBlock = new ItemMultiTexture(block, block, ((IBlockVariant) block)::getName);

    } else {
      itemBlock = new ItemBlock(block);
    }

    BlockRegistrationUtil.setRegistryName(block, itemBlock);
    return itemBlock;
  }

  private static void setRegistryName(Block block, ItemBlock itemBlock) {

    ResourceLocation registryName = block.getRegistryName();
    Preconditions.checkNotNull(registryName, "Block %s has null registry name", block);
    itemBlock.setRegistryName(registryName);
  }

}
