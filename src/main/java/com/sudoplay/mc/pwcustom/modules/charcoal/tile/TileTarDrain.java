package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockTarDrain;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileTarDrain
    extends TileTarTankBase {

  @Override
  protected List<BlockPos> getCollectionSourcePositions(World world, BlockPos origin) {

    IBlockState blockState = world.getBlockState(origin);

    if (blockState.getBlock() != ModuleCharcoal.Blocks.TAR_DRAIN) {
      return Collections.emptyList();
    }

    EnumFacing facing = blockState.getValue(BlockTarDrain.FACING).getOpposite();
    BlockPos offset = origin.offset(facing, 2);
    List<BlockPos> result = new ArrayList<>(9);

    for (int x = -1; x <= 1; x++) {

      for (int z = -1; z <= 1; z++) {
        result.add(offset.add(x, 0, z));
      }
    }

    return result;
  }

  @Override
  protected int getTankCapacity() {

    return 1000;
  }

  @Nullable
  @Override
  protected FluidTank getCollectionSourceFluidTank(@Nullable TileEntity tileEntity) {

    if (tileEntity instanceof TileTarCollector) {
      return ((TileTarCollector) tileEntity).getFluidTank();

    } else if (tileEntity instanceof TileActivePileBase) {
      return ((TileActivePileBase) tileEntity).getFluidTank();
    }

    return null;
  }

}
