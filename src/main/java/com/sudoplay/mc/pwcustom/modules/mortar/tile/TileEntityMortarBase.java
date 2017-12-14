package com.sudoplay.mc.pwcustom.modules.mortar.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public abstract class TileEntityMortarBase
    extends TileEntity {

  protected int durability;
  protected ItemStackHandler itemStackHandler;

  public TileEntityMortarBase(int durability) {

    this.durability = durability;
    this.itemStackHandler = new ItemStackHandler(8) {

      @Override
      protected int getStackLimit(int slot, @Nonnull ItemStack stack) {

        return 1;
      }
    };
  }

  public ItemStackHandler getItemStackHandler() {

    return this.itemStackHandler;
  }

  public boolean canInsertItem(ItemStack itemStack) {

    if (itemStack.isEmpty()) {
      // Trying to add air.  :-/
      return false;
    }

    if (this.getFirstEmptySlotIndex() == -1) {
      // All slots are full.
      return false;
    }

    return true;
  }

  public void insertItem(ItemStack itemStack) {

    if (this.canInsertItem(itemStack)) {
      int index = this.getFirstEmptySlotIndex();

      ItemStack stackToInsert = itemStack.copy();
      stackToInsert.setCount(1);
      this.itemStackHandler.setStackInSlot(index, stackToInsert);
      itemStack.shrink(1);

      this.markDirty();
    }
  }

  public ItemStack removeItem() {

    int index = this.getLastNonEmptySlotIndex();

    if (index == -1) {
      return ItemStack.EMPTY;
    }

    ItemStack toReturn = this.itemStackHandler.getStackInSlot(index);
    this.itemStackHandler.setStackInSlot(index, ItemStack.EMPTY);

    this.markDirty();

    return toReturn;
  }

  public int getItemCount() {

    int index = this.getFirstEmptySlotIndex();

    if (index == -1) {
      return this.itemStackHandler.getSlots();
    }

    return index;
  }

  public boolean isEmpty() {

    return this.getItemCount() == 0;
  }

  public void markDirty() {

    super.markDirty();

    if (this.world != null) {
      IBlockState blockState = world.getBlockState(this.getPos());
      this.world.notifyBlockUpdate(this.getPos(), blockState, blockState, 3);
    }
  }

  private int getFirstEmptySlotIndex() {

    for (int i = 0; i < this.itemStackHandler.getSlots(); i++) {

      if (this.itemStackHandler.getStackInSlot(i).isEmpty()) {
        return i;
      }
    }

    return -1;
  }

  private int getLastNonEmptySlotIndex() {

    for (int i = this.itemStackHandler.getSlots() - 1; i >= 0; i--) {

      if (!this.itemStackHandler.getStackInSlot(i).isEmpty()) {
        return i;
      }
    }

    return -1;
  }

  public boolean canPlayerUse(EntityPlayer player) {

    return this.getWorld().getTileEntity(this.getPos()) == this
        && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {

    compound = super.writeToNBT(compound);
    compound.setInteger("durability", this.durability);
    compound.setTag("itemStackHandler", this.itemStackHandler.serializeNBT());
    return compound;
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {

    super.readFromNBT(compound);
    this.durability = compound.getInteger("durability");
    this.itemStackHandler.deserializeNBT(compound.getCompoundTag("itemStackHandler"));
  }

  @Override
  public SPacketUpdateTileEntity getUpdatePacket() {

    return new SPacketUpdateTileEntity(this.pos, -1, this.getUpdateTag());
  }

  @Override
  public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {

    this.readFromNBT(packet.getNbtCompound());
  }

  @Override
  public final NBTTagCompound getUpdateTag() {

    return this.writeToNBT(new NBTTagCompound());
  }
}
