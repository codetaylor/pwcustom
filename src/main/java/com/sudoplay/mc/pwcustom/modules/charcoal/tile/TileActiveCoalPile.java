package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoalConfig;
import com.sudoplay.mc.pwcustom.modules.charcoal.Registries;
import com.sudoplay.mc.pwcustom.util.BlockMetaMatcher;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileActiveCoalPile
    extends TileActivePileBase {

  @Override
  protected FluidStack getFluidProducedPerBurnStage() {

    return FluidRegistry.getFluidStack(
        ModuleCharcoalConfig.COAL_BLOCK.FLUID_PRODUCED,
        ModuleCharcoalConfig.COAL_BLOCK.FLUID_PRODUCED_AMOUNT_MB
    );
  }

  @Override
  protected int getTotalBurnTimeTicks() {

    return ModuleCharcoalConfig.COAL_BLOCK.BURN_TIME_TICKS;
  }

  @Override
  protected int getTotalStages() {

    return ModuleCharcoalConfig.COAL_BLOCK.BURN_STAGES;
  }

  @Override
  protected void onAllBurnStagesComplete() {

    IBlockState state = ModuleCharcoal.Blocks.COAL_PILE_ASH.getDefaultState();
    this.world.setBlockState(this.pos, state);
  }

  @Override
  protected boolean isValidStructureBlock(World world, BlockPos pos, IBlockState blockState, EnumFacing facing) {

    for (BlockMetaMatcher matcher : Registries.COKE_OVEN_VALID_STRUCTURE_BLOCK_LIST) {

      if (matcher.test(blockState)) {
        return true;
      }
    }

    for (BlockMetaMatcher matcher : Registries.REFRACTORY_BLOCK_LIST) {

      if (matcher.test(blockState)) {
        return true;
      }
    }

    return false;
  }
}
