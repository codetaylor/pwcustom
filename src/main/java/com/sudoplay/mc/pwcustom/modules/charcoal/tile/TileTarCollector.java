package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoalConfig;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class TileTarCollector
    extends TileTarTankBase {

  @Override
  public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {

    return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
        && facing == EnumFacing.UP);
  }

  @Nullable
  @Override
  public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {

    if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
        && facing == EnumFacing.UP) {
      //noinspection unchecked
      return (T) this.fluidTank;
    }

    return super.getCapability(capability, facing);
  }

  @Override
  protected List<BlockPos> getCollectionSourcePositions(World world, BlockPos origin) {

    return Collections.emptyList();
  }

  @Override
  protected int getTankCapacity() {

    return ModuleCharcoalConfig.GENERAL.TAR_COLLECTOR_CAPACITY;
  }

  @Nullable
  @Override
  protected FluidTank getCollectionSourceFluidTank(@Nullable TileEntity tileEntity) {

    if (tileEntity instanceof TileActivePile) {
      return ((TileActivePile) tileEntity).getFluidTank();
    }

    return null;
  }

}
