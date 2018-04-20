package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import net.minecraft.block.state.IBlockState;

public class TileActiveLogPile
    extends TileActivePileBase {

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

    return ModuleCharcoal.Blocks.LOG_PILE_ASH.getDefaultState();
  }

  @Override
  protected int getTotalStages() {

    return 9;
  }
}
