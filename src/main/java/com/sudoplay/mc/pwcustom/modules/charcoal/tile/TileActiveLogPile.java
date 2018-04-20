package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleFluids;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileActiveLogPile
    extends TileActivePileBase {

  @Override
  protected FluidStack getFluidProducedPerItem() {

    return FluidRegistry.getFluidStack(ModuleFluids.WOOD_TAR.getName(), 50);
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
