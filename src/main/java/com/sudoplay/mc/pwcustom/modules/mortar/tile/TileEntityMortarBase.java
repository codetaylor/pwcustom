package com.sudoplay.mc.pwcustom.modules.mortar.tile;

import com.sudoplay.mc.pwcustom.lib.IRecipeOutputProvider;
import com.sudoplay.mc.pwcustom.lib.util.StackUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileEntityMortarBase
    extends TileEntity
    implements IMortar {

  protected int durability;
  protected MortarDelegateFactory mortarDelegateFactory;
  protected IMortar mortarDelegate;
  protected int craftingProgress;

  public TileEntityMortarBase(int durability) {

    this.durability = durability;
    this.mortarDelegateFactory = new MortarDelegateFactory(this::markDirty);
    this.mortarDelegate = this.mortarDelegateFactory.create(EnumMortarMode.MIXING);
  }

  public EnumMortarMode cycleMortarMode() {

    if (this.isEmpty()) {
      int id = this.mortarDelegate.getMortarMode().getId();
      id = (id + 1) % EnumMortarMode.values().length;
      this.mortarDelegate = this.mortarDelegateFactory.create(EnumMortarMode.fromId(id));
      this.markDirty();
    }

    return this.mortarDelegate.getMortarMode();
  }

  @Override
  public EnumMortarMode getMortarMode() {

    return this.mortarDelegate.getMortarMode();
  }

  @Override
  public String getMortarModeString() {

    return this.mortarDelegate.getMortarModeString();
  }

  @Override
  public ItemStackHandler getItemStackHandler() {

    return this.mortarDelegate.getItemStackHandler();
  }

  @Override
  public boolean canInsertItem(ItemStack itemStack) {

    return this.mortarDelegate.canInsertItem(itemStack);
  }

  @Override
  public void insertItem(ItemStack itemStack) {

    this.mortarDelegate.insertItem(itemStack);
  }

  @Override
  public ItemStack removeItem() {

    this.resetCraftingProgress();
    return this.mortarDelegate.removeItem();
  }

  @Override
  public int getItemCount() {

    return this.mortarDelegate.getItemCount();
  }

  @Override
  public boolean isEmpty() {

    return this.mortarDelegate.isEmpty();
  }

  public void resetCraftingProgress() {

    this.craftingProgress = 0;
    this.markDirty();
  }

  public void incrementCraftingProgress() {

    if (this.hasRecipe()) {
      this.craftingProgress += 1;
      this.markDirty();

      if (this.craftingProgress >= 10) {
        this.resetCraftingProgress();

        ItemStack itemStack = this.doCrafting();
        StackUtil.spawnStackOnTop(this.world, itemStack, this.pos);
      }
    }
  }

  @Override
  public ItemStack doCrafting() {

    return this.mortarDelegate.doCrafting();
  }

  public boolean hasRecipe() {

    return this.getRecipe() != null;
  }

  @Override
  public IRecipeOutputProvider getRecipe() {

    return this.mortarDelegate.getRecipe();
  }

  public void markDirty() {

    super.markDirty();

    if (this.world != null) {
      IBlockState blockState = world.getBlockState(this.getPos());
      this.world.notifyBlockUpdate(this.getPos(), blockState, blockState, 3);
    }
  }

  public boolean canPlayerUse(EntityPlayer player) {

    return this.getWorld().getTileEntity(this.getPos()) == this
        && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {

    compound = super.writeToNBT(compound);
    compound.setInteger("durability", this.durability);
    compound.setInteger("mortarMode", this.mortarDelegate.getMortarMode().getId());
    compound.setTag("mortarDelegate", this.mortarDelegate.serializeNBT());
    compound.setInteger("craftingProgress", this.craftingProgress);

    return compound;
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {

    super.readFromNBT(compound);
    this.durability = compound.getInteger("durability");
    this.craftingProgress = compound.getInteger("craftingProgress");

    EnumMortarMode mortarMode = EnumMortarMode.fromId(compound.getInteger("mortarMode"));
    if (mortarMode != this.mortarDelegate.getMortarMode()) {
      this.mortarDelegate = this.mortarDelegateFactory.create(mortarMode);
    }
    this.mortarDelegate.deserializeNBT(compound.getCompoundTag("mortarDelegate"));
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
