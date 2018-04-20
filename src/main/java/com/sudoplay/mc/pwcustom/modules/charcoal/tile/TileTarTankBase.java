package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class TileTarTankBase
    extends TileEntity
    implements ITickable {

  private static final int UPDATE_DELAY_TICKS = 20;

  protected FluidTank fluidTank;
  protected int ticksUntilNextUpdate;

  /* package */ TileTarTankBase() {

    this.fluidTank = new FluidTank(this.getTankCapacity());
    this.fluidTank.setCanFill(false);
  }

  public FluidTank getFluidTank() {

    return this.fluidTank;
  }

  @Override
  public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {

    return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
  }

  @Nullable
  @Override
  public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {

    if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
      //noinspection unchecked
      return (T) this.fluidTank;
    }

    return super.getCapability(capability, facing);
  }

  @Override
  public void update() {

    if (this.world.isRemote) {
      return;
    }

    if (this.ticksUntilNextUpdate > 0) {
      this.ticksUntilNextUpdate -= 1;

    } else {
      this.ticksUntilNextUpdate = UPDATE_DELAY_TICKS;
      this.collect(this.pos, this.fluidTank, this.world);
    }
  }

  private void collect(BlockPos pos, FluidTank fluidTank, World world) {

    if (fluidTank.getFluidAmount() == fluidTank.getCapacity()) {
      return;
    }

    List<BlockPos> sourcePositions = this.getCollectionSourcePositions(world, pos);

    for (BlockPos sourcePosition : sourcePositions) {
      TileEntity tileEntity = world.getTileEntity(sourcePosition);
      FluidTank sourceFluidTank = this.getCollectionSourceFluidTank(tileEntity);

      if (sourceFluidTank != null) {
        this.collect(sourceFluidTank, fluidTank);
      }
    }
  }

  private void collect(FluidTank source, FluidTank target) {

    if (source.getFluidAmount() > 0) {
      source.drain(target.fillInternal(source.getFluid(), true), true);
    }
  }

  @Nonnull
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {

    super.writeToNBT(compound);
    compound.setTag("fluidTank", this.fluidTank.writeToNBT(new NBTTagCompound()));
    return compound;
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {

    super.readFromNBT(compound);
    this.fluidTank.readFromNBT(compound.getCompoundTag("fluidTank"));
  }

  protected abstract List<BlockPos> getCollectionSourcePositions(World world, BlockPos origin);

  protected abstract int getTankCapacity();

  @Nullable
  protected abstract FluidTank getCollectionSourceFluidTank(@Nullable TileEntity tileEntity);
}
