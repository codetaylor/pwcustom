package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleFluids;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileActiveLogPile
    extends TileActivePileBase {

  @Override
  protected FluidStack getFluidProducedPerBurnStage() {

    return FluidRegistry.getFluidStack(ModuleFluids.WOOD_TAR.getName(), 50);
  }

  @Override
  protected int getTotalBurnTimeTicks() {

    return 1000;
  }

  @Override
  protected void onAllBurnStagesComplete() {

    IBlockState state = ModuleCharcoal.Blocks.LOG_PILE_ASH.getDefaultState();
    this.world.setBlockState(this.pos, state);
  }

  @Override
  protected int getTotalStages() {

    return 10;
  }

}
