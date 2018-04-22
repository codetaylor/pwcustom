package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import com.codetaylor.mc.athenaeum.util.BlockHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

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

    this.fluidTank = new FluidTank(this.getTankCapacity()) {

      @Override
      protected void onContentsChanged() {

        TileTarTankBase tileTarTankBase = TileTarTankBase.this;

        if (!tileTarTankBase.world.isRemote) {
          BlockHelper.notifyBlockUpdate(tileTarTankBase.world, tileTarTankBase.pos);
        }
      }
    };
    this.fluidTank.setCanFill(false);
  }

  public FluidTank getFluidTank() {

    return this.fluidTank;
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

  @Nonnull
  @Override
  public NBTTagCompound getUpdateTag() {

    return this.writeToNBT(new NBTTagCompound());
  }

  @Nullable
  @Override
  public SPacketUpdateTileEntity getUpdatePacket() {

    return new SPacketUpdateTileEntity(this.pos, -1, this.getUpdateTag());
  }

  @Override
  public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet) {

    this.readFromNBT(packet.getNbtCompound());
  }

  protected abstract List<BlockPos> getCollectionSourcePositions(World world, BlockPos origin);

  protected abstract int getTankCapacity();

  @Nullable
  protected abstract FluidTank getCollectionSourceFluidTank(@Nullable TileEntity tileEntity);
}
