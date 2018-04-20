package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nonnull;

public abstract class TileActivePileBase
    extends TileEntity
    implements ITickable {

  private static final int DEFAULT_MAX_CREOSOTE_LEVEL = 1000;
  private static final int DEFAULT_MAX_INVALID_TICKS = 100;

  private boolean needStructureValidation;
  private int burnTimeTicksPerStage;
  private int invalidTicks;
  private int remainingStages;
  private FluidTank fluidTank;

  public TileActivePileBase() {

    this.invalidTicks = 0;
    this.remainingStages = this.getTotalStages();
    this.fluidTank = new FluidTank(this.getMaxCreosoteLevel());
  }

  public FluidTank getFluidTank() {

    return this.fluidTank;
  }

  @Override
  public void update() {

    if (this.world.isRemote) {
      return;
    }

    this.checkValid();

    if (this.burnTimeTicksPerStage > 0) {
      this.burnTimeTicksPerStage -= 1;

    } else {

      if (this.remainingStages > 0) {
        this.remainingStages -= 1;

        this.fluidTank.fill(
            this.getFluidProducedPerItem(),
            true
        );

        this.burnTimeTicksPerStage = this.getTotalBurnTimeTicks() / this.getTotalStages();

      } else {
        this.world.setBlockState(this.pos, this.getResultingBlockState());
      }
    }

    if (this.fluidTank.getFluidAmount() > 0) {
      TileEntity tileEntity = this.world.getTileEntity(this.pos.offset(EnumFacing.DOWN));

      if (tileEntity instanceof TileActivePileBase) {
        TileActivePileBase down = (TileActivePileBase) tileEntity;
        this.fluidTank.drain(down.fluidTank.fill(this.fluidTank.getFluid(), true), true);
      }
    }
  }

  protected void checkValid() {

    if (!this.needStructureValidation) {
      return;
    }

    if (this.isStructureValid()) {
      this.invalidTicks = 0;
      this.needStructureValidation = false;

    } else {

      if (this.invalidTicks < this.getMaxInvalidTicks()) {
        this.invalidTicks += 1;

        for (EnumFacing facing : EnumFacing.VALUES) {
          BlockPos offset = this.pos.offset(facing);
          IBlockState blockState = this.world.getBlockState(offset);
          Block block = blockState.getBlock();

          if (block.isAir(blockState, this.world, offset) ||
              block.isReplaceable(this.world, offset)) {
            this.world.setBlockState(offset, Blocks.FIRE.getDefaultState());
          }
        }

      } else {
        this.world.setBlockToAir(this.pos);
      }
    }
  }

  protected boolean isStructureValid() {

    for (EnumFacing facing : EnumFacing.VALUES) {
      BlockPos offset = this.pos.offset(facing);
      IBlockState blockState = this.world.getBlockState(offset);
      EnumFacing opposite = facing.getOpposite();

      if ((!this.isValidStructureBlock(blockState)) ||
          (!blockState.isSideSolid(this.world, offset, opposite)) ||
          blockState.getBlock().isFlammable(this.world, offset, opposite)) {
        return false;
      }
    }
    return true;
  }

  @Nonnull
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {

    super.writeToNBT(compound);

    compound.setBoolean("needStructureValidation", this.needStructureValidation);
    compound.setInteger("burnTimeTicksPerStage", this.burnTimeTicksPerStage);
    compound.setInteger("invalidTicks", this.invalidTicks);
    compound.setInteger("remainingStages", this.remainingStages);
    compound.setTag("fluidTank", this.fluidTank.writeToNBT(new NBTTagCompound()));
    return compound;
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {

    super.readFromNBT(compound);

    this.needStructureValidation = compound.getBoolean("needStructureValidation");
    this.burnTimeTicksPerStage = compound.getInteger("burnTimeTicksPerStage");
    this.invalidTicks = compound.getInteger("invalidTicks");
    this.remainingStages = compound.getInteger("remainingStages");
    this.fluidTank.readFromNBT(compound.getCompoundTag("fluidTank"));
  }

  public void setNeedStructureValidation() {

    this.needStructureValidation = true;
  }

  protected boolean isValidStructureBlock(IBlockState blockState) {

    return true;
  }

  protected int getMaxCreosoteLevel() {

    return DEFAULT_MAX_CREOSOTE_LEVEL;
  }

  protected int getMaxInvalidTicks() {

    return DEFAULT_MAX_INVALID_TICKS;
  }

  protected abstract FluidStack getFluidProducedPerItem();

  protected abstract int getTotalBurnTimeTicks();

  protected abstract IBlockState getResultingBlockState();

  protected abstract int getTotalStages();

}
