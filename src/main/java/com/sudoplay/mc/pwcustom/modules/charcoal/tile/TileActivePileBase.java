package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;

public abstract class TileActivePileBase
    extends TileBurnableBase {

  private static final int DEFAULT_MAX_CREOSOTE_LEVEL = 1000;

  protected FluidTank fluidTank;

  public TileActivePileBase() {

    super();
    this.fluidTank = new FluidTank(this.getMaxCreosoteLevel());
  }

  public FluidTank getFluidTank() {

    return this.fluidTank;
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

  protected int getMaxCreosoteLevel() {

    return DEFAULT_MAX_CREOSOTE_LEVEL;
  }

  @Override
  protected void onUpdate() {

    this.pushTarDown();
  }

  @Override
  protected void onBurnStageComplete() {

    this.fluidTank.fill(
        this.getFluidProducedPerBurnStage(),
        true
    );
    this.pushTarDown();
  }

  @Override
  protected void onUpdateValid() {
    //
  }

  @Override
  protected void onUpdateInvalid() {

    for (EnumFacing facing : EnumFacing.VALUES) {
      BlockPos offset = this.pos.offset(facing);
      IBlockState blockState = this.world.getBlockState(offset);
      Block block = blockState.getBlock();

      if (block.isAir(blockState, this.world, offset) ||
          block.isReplaceable(this.world, offset)) {
        this.world.setBlockState(offset, Blocks.FIRE.getDefaultState());
      }
    }
  }

  @Override
  protected void onInvalidDelayExpired() {

    this.world.setBlockToAir(this.pos);
  }

  @Override
  protected boolean isActive() {

    return true;
  }

  private void pushTarDown() {

    if (this.fluidTank.getFluidAmount() > 0) {
      TileEntity tileEntity = this.world.getTileEntity(this.pos.offset(EnumFacing.DOWN));

      if (tileEntity instanceof TileActivePileBase) {
        TileActivePileBase down = (TileActivePileBase) tileEntity;
        this.fluidTank.drain(down.fluidTank.fillInternal(this.fluidTank.getFluid(), true), true);

      } else if (tileEntity instanceof TileTarCollector) {
        TileTarCollector down = (TileTarCollector) tileEntity;
        this.fluidTank.drain(down.fluidTank.fillInternal(this.fluidTank.getFluid(), true), true);
      }
    }
  }

  /**
   * @return the fluid stack produced per burn stage
   */
  protected abstract FluidStack getFluidProducedPerBurnStage();

}
