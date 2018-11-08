package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import com.codetaylor.mc.athenaeum.util.BlockHelper;
import com.codetaylor.mc.athenaeum.util.StackHelper;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockCampfire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileCampfire
    extends TileEntity
    implements ITickable {

  private ItemStackHandler fuelStackHandler;

  public TileCampfire() {

    this.fuelStackHandler = new ItemStackHandler(1) {

      @Override
      protected int getStackLimit(int slot, @Nonnull ItemStack stack) {

        return 8;
      }

      @Override
      protected void onContentsChanged(int slot) {

        TileCampfire.this.markDirty();
        BlockHelper.notifyBlockUpdate(TileCampfire.this.world, TileCampfire.this.pos);
      }

    };

  }

  public ItemStackHandler getFuelStackHandler() {

    return this.fuelStackHandler;
  }

  public BlockCampfire.EnumType getState() {

    return BlockCampfire.EnumType.NORMAL;
  }

  public int getFuelRemaining() {

    // 0 is no wood
    // [0, 8]
    ItemStack itemStack = this.fuelStackHandler.getStackInSlot(0);

    if (itemStack.isEmpty()) {
      return 0;
    }

    return itemStack.getCount();
  }

  @Override
  public void update() {

  }

  @Nonnull
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {

    super.writeToNBT(compound);
    compound.setTag("fuelStackHandler", this.fuelStackHandler.serializeNBT());
    return compound;
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {

    super.readFromNBT(compound);
    this.fuelStackHandler.deserializeNBT(compound.getCompoundTag("fuelStackHandler"));
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
    BlockHelper.notifyBlockUpdate(this.world, this.pos);
  }

  @Override
  public boolean shouldRefresh(
      World world,
      BlockPos pos,
      @Nonnull IBlockState oldState,
      @Nonnull IBlockState newState
  ) {

    if (oldState.getBlock() == newState.getBlock()) {
      return false;
    }

    return super.shouldRefresh(world, pos, oldState, newState);
  }

  public void removeItems() {

    ItemStackHandler stackHandler = this.getFuelStackHandler();
    ItemStack itemStack = stackHandler.extractItem(0, 64, false);

    if (!itemStack.isEmpty()) {
      StackHelper.spawnStackOnTop(this.world, itemStack, this.pos);
    }

    BlockHelper.notifyBlockUpdate(this.world, this.pos);
  }
}
