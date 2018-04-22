package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoalConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileActiveLogPile
    extends TileActivePileBase {

  @Override
  protected FluidStack getFluidProducedPerBurnStage() {

    return FluidRegistry.getFluidStack(
        ModuleCharcoalConfig.LOG_PILE.FLUID_PRODUCED,
        ModuleCharcoalConfig.LOG_PILE.FLUID_PRODUCED_AMOUNT_MB
    );
  }

  @Override
  protected int getTotalBurnTimeTicks() {

    return ModuleCharcoalConfig.LOG_PILE.BURN_TIME_TICKS;
  }

  @Override
  protected int getTotalStages() {

    return ModuleCharcoalConfig.LOG_PILE.BURN_STAGES;
  }

  @Override
  protected void onAllBurnStagesComplete() {

    IBlockState state = ModuleCharcoal.Blocks.LOG_PILE_ASH.getDefaultState();
    this.world.setBlockState(this.pos, state);
  }

}
