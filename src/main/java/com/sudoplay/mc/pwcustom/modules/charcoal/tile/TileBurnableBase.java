package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class TileBurnableBase
    extends TileEntity
    implements ITickable {

  private static final int DEFAULT_MAX_INVALID_TICKS = 100;

  protected boolean needStructureValidation;
  protected int burnTimeTicksPerStage;
  protected int invalidTicks;
  protected int remainingStages;

  public TileBurnableBase() {

    this.remainingStages = this.getTotalStages();
    this.invalidTicks = 0;
  }

  @Override
  public void update() {

    if (this.world.isRemote) {
      return;
    }

    if (!this.isActive()) {
      return;
    }

    this.onUpdate();

    if (this.needStructureValidation) {

      if (this.isStructureValid()) {
        this.invalidTicks = 0;
        this.needStructureValidation = false;
      }
    }

    if (!this.needStructureValidation) {
      this.onUpdateValid();

    } else {

      if (this.invalidTicks < this.getMaxInvalidTicks()) {
        this.invalidTicks += 1;
        this.onUpdateInvalid();

      } else {
        this.onInvalidDelayExpired();
      }
    }

    if (this.burnTimeTicksPerStage > 0) {
      this.burnTimeTicksPerStage -= 1;

    } else {

      if (this.remainingStages > 0) {
        this.remainingStages -= 1;
        this.onBurnStageComplete();
        this.reset();

      } else {
        this.onAllBurnStagesComplete();
      }
    }
  }

  protected void reset() {

    this.burnTimeTicksPerStage = this.getTotalBurnTimeTicks() / this.getTotalStages();
  }

  protected boolean isStructureValid() {

    for (EnumFacing facing : EnumFacing.VALUES) {

      BlockPos offset = this.pos.offset(facing);

      if (!this.isValidStructureBlock(this.world, offset, this.world.getBlockState(offset), facing.getOpposite())) {
        return false;
      }
    }
    return true;
  }

  protected boolean isValidStructureBlock(World world, BlockPos pos, IBlockState blockState, EnumFacing facing) {

    return blockState.isSideSolid(world, pos, facing) &&
        !blockState.getBlock().isFlammable(world, pos, facing);
  }

  @Nonnull
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {

    super.writeToNBT(compound);

    compound.setBoolean("needStructureValidation", this.needStructureValidation);
    compound.setInteger("burnTimeTicksPerStage", this.burnTimeTicksPerStage);
    compound.setInteger("invalidTicks", this.invalidTicks);
    compound.setInteger("remainingStages", this.remainingStages);
    return compound;
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {

    super.readFromNBT(compound);

    this.needStructureValidation = compound.getBoolean("needStructureValidation");
    this.burnTimeTicksPerStage = compound.getInteger("burnTimeTicksPerStage");
    this.invalidTicks = compound.getInteger("invalidTicks");
    this.remainingStages = compound.getInteger("remainingStages");
  }

  protected int getMaxInvalidTicks() {

    return DEFAULT_MAX_INVALID_TICKS;
  }

  public void setNeedStructureValidation() {

    this.needStructureValidation = true;
  }

  /**
   * @return false to prevent the update loop from running
   */
  protected abstract boolean isActive();

  /**
   * Called on the server on every update tick.
   */
  protected abstract void onUpdate();

  /**
   * Called on the server for every tick this burnable is valid.
   */
  protected abstract void onUpdateValid();

  /**
   * Called on the server for every tick this burnable has an invalid structure.
   */
  protected abstract void onUpdateInvalid();

  /**
   * Called when the invalid delay expires.
   */
  protected abstract void onInvalidDelayExpired();

  /**
   * Called once for each completed burn stage.
   */
  protected abstract void onBurnStageComplete();

  /**
   * @return the total burn time for all stages in ticks
   */
  protected abstract int getTotalBurnTimeTicks();

  /**
   * Called when all burn stages are complete.
   */
  protected abstract void onAllBurnStagesComplete();

  /**
   * @return the total number of burn stages
   */
  protected abstract int getTotalStages();

}
