package com.sudoplay.mc.pwcustom.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.oredict.OreDictionary;

import java.util.function.Predicate;

public class BlockMetaMatcher
    implements Predicate<IBlockState> {

  private final Block block;
  private final int meta;

  public BlockMetaMatcher(Block block, int meta) {

    this.block = block;
    this.meta = meta;
  }

  @Override
  public boolean test(IBlockState blockState) {

    Block blockCandidate = blockState.getBlock();

    if (blockCandidate != this.block) {
      return false;
    }

    return (this.meta == OreDictionary.WILDCARD_VALUE)
        || (this.block.getStateFromMeta(this.meta) == blockState);
  }
}
