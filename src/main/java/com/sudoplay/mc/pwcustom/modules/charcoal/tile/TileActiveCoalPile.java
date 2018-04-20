package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.util.BlockMetaMatcher;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class TileActiveCoalPile
    extends TileActivePileBase {

  public static final List<BlockMetaMatcher> VALID_STRUCTURE_BLOCK_LIST;

  static {
    VALID_STRUCTURE_BLOCK_LIST = new ArrayList<>();
    VALID_STRUCTURE_BLOCK_LIST.add(new BlockMetaMatcher(ModuleCharcoal.Blocks.COAL_PILE_ACTIVE, 0));
    VALID_STRUCTURE_BLOCK_LIST.add(new BlockMetaMatcher(ModuleCharcoal.Blocks.COAL_PILE_ASH, 0));
    VALID_STRUCTURE_BLOCK_LIST.add(new BlockMetaMatcher(Blocks.BRICK_BLOCK, 0));
    VALID_STRUCTURE_BLOCK_LIST.add(new BlockMetaMatcher(Blocks.IRON_DOOR, OreDictionary.WILDCARD_VALUE));
  }

  @Override
  protected int getFluidProducedPerItem() {

    return 50;
  }

  @Override
  protected int getTotalBurnTimeTicks() {

    return 1000;
  }

  @Override
  protected IBlockState getResultingBlockState() {

    return ModuleCharcoal.Blocks.COAL_PILE_ASH.getDefaultState();
  }

  @Override
  protected int getTotalStages() {

    return 9;
  }

  @Override
  protected boolean isValidStructureBlock(IBlockState blockState) {

    for (BlockMetaMatcher matcher : VALID_STRUCTURE_BLOCK_LIST) {

      if (matcher.test(blockState)) {
        return true;
      }
    }

    return false;
  }
}
