package com.sudoplay.mc.pwcustom.modules.mortar.tile;

import com.sudoplay.mc.pwcustom.lib.util.StackUtil;
import com.sudoplay.mc.pwcustom.modules.mortar.ModuleMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.recipe.IRecipeMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarMode;
import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public abstract class TileEntityMortarBase
    extends TileEntity
    implements IMortar {

  protected byte typeId;
  protected int durability;
  protected FactoryMortarDelegate factoryMortarDelegate;
  protected IMortar mortarDelegate;
  protected int craftingProgress;

  public TileEntityMortarBase(EnumMortarType typeId) {

    this.typeId = (byte) typeId.getMeta();
    this.factoryMortarDelegate = new FactoryMortarDelegate(this::markDirty);
    this.mortarDelegate = this.factoryMortarDelegate.create(
        EnumMortarType.fromMeta(this.typeId),
        EnumMortarMode.MIXING
    );
  }

  public EnumMortarMode cycleMortarMode() {

    if (this.isEmpty()) {
      int id = this.mortarDelegate.getMortarMode().getId();
      id = (id + 1) % EnumMortarMode.values().length;
      this.mortarDelegate = this.factoryMortarDelegate.create(
          EnumMortarType.fromMeta(this.typeId),
          EnumMortarMode.fromId(id)
      );
      this.markDirty();
    }

    return this.mortarDelegate.getMortarMode();
  }

  public int getCraftingProgress() {

    return this.craftingProgress;
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

  @Override
  public void dropAllItems(World world, BlockPos pos) {

    this.mortarDelegate.dropAllItems(world, pos);
  }

  private void resetCraftingProgress() {

    this.craftingProgress = 0;
    this.markDirty();
  }

  public void incrementCraftingProgress() {

    IRecipeMortar recipe = this.getRecipe();

    if (recipe != null) {
      this.craftingProgress += 1;
      this.markDirty();

      if (this.craftingProgress >= recipe.getDuration()) {
        this.resetCraftingProgress();

        ItemStack itemStack = this.doCrafting();
        StackUtil.spawnStackOnTop(this.world, itemStack, this.pos);

        // check durability
        int maxDurability = this.getMaxDurability();

        if (maxDurability > 0) {
          this.incrementAndCheckDurability(maxDurability);
        }
      }
    }
  }

  private void incrementAndCheckDurability(int maxDurability) {

    this.durability += 1;

    if (this.durability >= maxDurability) {
      this.destroy(true, true, SoundEvents.ENTITY_ITEM_BREAK);
    }
  }

  public ItemStack destroy(boolean dropAllItems, boolean spawnParticles, @Nullable SoundEvent soundEvent) {

    if (dropAllItems) {
      this.dropAllItems(this.world, this.pos);
    }

    if (spawnParticles) {
      ((WorldServer) this.world).spawnParticle(
          EnumParticleTypes.BLOCK_CRACK,
          this.pos.getX() + 0.5,
          this.pos.getY() + 0.125,
          this.pos.getZ() + 0.5,
          50,
          0,
          0,
          0,
          2d,
          Block.getStateId(this.world.getBlockState(this.pos))
      );
    }

    if (soundEvent != null) {
      this.world.playSound(
          null,
          this.pos.getX() + 0.5,
          this.pos.getY() + 0.125,
          this.pos.getZ() + 0.5,
          soundEvent,
          SoundCategory.BLOCKS,
          1.0f,
          1.0f
      );
    }

    this.world.setBlockToAir(this.pos);

    return this.getAsItemStack();
  }

  public ItemStack getAsItemStack() {

    ItemStack itemStack = new ItemStack(Item.getItemFromBlock(ModuleMortar.Blocks.MORTAR), 1, this.typeId);
    NBTTagCompound compound = new NBTTagCompound();
    NBTTagCompound teCompound = new NBTTagCompound();
    this.writeToNBT(teCompound);
    compound.setTag("BlockEntityTag", teCompound);
    itemStack.setTagCompound(compound);
    return itemStack;
  }

  @Override
  public ItemStack doCrafting() {

    return this.mortarDelegate.doCrafting();
  }

  @Override
  public IRecipeMortar getRecipe() {

    return this.mortarDelegate.getRecipe();
  }

  public void markDirty() {

    super.markDirty();

    if (this.world != null) {
      IBlockState blockState = world.getBlockState(this.getPos());
      this.world.notifyBlockUpdate(this.getPos(), blockState, blockState, 3);
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {

    compound = super.writeToNBT(compound);
    compound.setByte("type", this.typeId);
    compound.setInteger("durability", this.durability);
    compound.setInteger("mortarMode", this.mortarDelegate.getMortarMode().getId());
    compound.setTag("mortarDelegate", this.mortarDelegate.serializeNBT());
    compound.setInteger("craftingProgress", this.craftingProgress);

    return compound;
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {

    super.readFromNBT(compound);
    this.typeId = compound.getByte("type");
    this.durability = compound.getInteger("durability");
    this.craftingProgress = compound.getInteger("craftingProgress");

    EnumMortarMode mortarMode = EnumMortarMode.fromId(compound.getInteger("mortarMode"));
    if (mortarMode != this.mortarDelegate.getMortarMode()) {
      this.mortarDelegate = this.factoryMortarDelegate.create(EnumMortarType.fromMeta(this.typeId), mortarMode);
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

  private int getMaxDurability() {

    return ModuleMortar.Blocks.MORTAR.getMaxDurability(EnumMortarType.fromMeta(this.typeId));
  }

}
