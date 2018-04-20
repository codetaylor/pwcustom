package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class TileTarCollector
    extends TileTarTankBase {

  @Override
  protected List<BlockPos> getCollectionSourcePositions(World world, BlockPos origin) {

    return Collections.singletonList(origin.up());
  }

  @Override
  protected int getTankCapacity() {

    return 8000;
  }

  @Nullable
  @Override
  protected FluidTank getCollectionSourceFluidTank(@Nullable TileEntity tileEntity) {

    if (tileEntity instanceof TileActivePileBase) {
      return ((TileActivePileBase) tileEntity).getFluidTank();
    }

    return null;
  }

}
