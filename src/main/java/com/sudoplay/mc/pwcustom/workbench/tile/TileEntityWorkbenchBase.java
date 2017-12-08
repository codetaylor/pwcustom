package com.sudoplay.mc.pwcustom.workbench.tile;

import com.sudoplay.mc.pwcustom.inventory.ObservableStackHandler;
import com.sudoplay.mc.pwcustom.recipe.IRecipeWorkbench;
import com.sudoplay.mc.pwcustom.recipe.RegistryRecipeWorkbenchBasic;
import com.sudoplay.mc.pwcustom.tile.IContainer;
import com.sudoplay.mc.pwcustom.util.StackUtil;
import com.sudoplay.mc.pwcustom.workbench.gui.CraftingMatrixStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class TileEntityWorkbenchBase
    extends TileEntity
    implements IContainer {

  protected ObservableStackHandler toolHandler;
  protected CraftingMatrixStackHandler craftingMatrixHandler;
  protected ItemStackHandler resultHandler;
  protected IRecipeWorkbench lastRecipeCrafted;

  public TileEntityWorkbenchBase(int width, int height) {

    this.craftingMatrixHandler = new CraftingMatrixStackHandler(width, height);
    this.craftingMatrixHandler.addObserver(this::onInputsChanged);

    this.toolHandler = new ObservableStackHandler(1);
    this.toolHandler.addObserver(this::onInputsChanged);

    this.resultHandler = new ItemStackHandler();
  }

  public ObservableStackHandler getToolHandler() {

    return this.toolHandler;
  }

  public CraftingMatrixStackHandler getCraftingMatrixHandler() {

    return this.craftingMatrixHandler;
  }

  public ItemStackHandler getResultHandler() {

    return this.resultHandler;
  }

  @Override
  public List<ItemStack> getBlockBreakDrops() {

    List<ItemStack> result = new ArrayList<>();

    result.add(this.toolHandler.getStackInSlot(0));

    int slotCount = this.craftingMatrixHandler.getSlots();

    for (int i = 0; i < slotCount; i++) {
      ItemStack itemStack = this.craftingMatrixHandler.getStackInSlot(i);

      if (!itemStack.isEmpty()) {
        result.add(itemStack);
      }
    }

    return result;
  }

  public boolean canPlayerUse(EntityPlayer player) {

    return this.getWorld().getTileEntity(this.getPos()) == this
        && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {

    tag = super.writeToNBT(tag);
    tag.setTag("craftingMatrixHandler", this.craftingMatrixHandler.serializeNBT());
    tag.setTag("toolHandler", this.toolHandler.serializeNBT());
    return tag;
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {

    super.readFromNBT(tag);
    this.craftingMatrixHandler.deserializeNBT(tag.getCompoundTag("craftingMatrixHandler"));
    this.toolHandler.deserializeNBT(tag.getCompoundTag("toolHandler"));
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

  public void onTakeResult(EntityPlayer player) {

    int slotCount = this.craftingMatrixHandler.getSlots();

    for (int i = 0; i < slotCount; i++) {
      ItemStack itemStack = this.craftingMatrixHandler.getStackInSlot(i);

      if (!itemStack.isEmpty()) {

        if (itemStack.getItem().hasContainerItem(itemStack)
            && itemStack.getCount() == 1) {
          this.craftingMatrixHandler.setStackInSlot(i, itemStack.getItem().getContainerItem(itemStack));

        } else {
          this.craftingMatrixHandler.setStackInSlot(i, StackUtil.decrease(itemStack.copy(), 1, false));
        }
      }
    }

    ItemStack itemStack = this.toolHandler.getStackInSlot(0);

    if (!itemStack.isEmpty()) {
      int itemDamage = itemStack.getMetadata() + this.lastRecipeCrafted.getToolDamage();

      if (itemDamage >= itemStack.getItem().getMaxDamage(itemStack)) {
        this.toolHandler.setStackInSlot(0, ItemStack.EMPTY);

        if (!this.world.isRemote) {
          this.world.playSound(
              player,
              player.posX,
              player.posY,
              player.posZ,
              SoundEvents.ENTITY_ITEM_BREAK,
              SoundCategory.PLAYERS,
              1.0f,
              1.0f
          );
        }

      } else {
        ItemStack copy = itemStack.copy();
        copy.setItemDamage(itemDamage);
        this.toolHandler.setStackInSlot(0, copy);
      }

    }
  }

  public void updateRecipe() {

    this.findResult();
    this.markDirty();
  }

  protected void onInputsChanged(ItemStackHandler stackHandler, int slotIndex) {

    this.updateRecipe();
  }

  protected void findResult() {

    RegistryRecipeWorkbenchBasic registry = this.getRecipeRegistry();
    IRecipeWorkbench recipe = registry.findRecipe(this.toolHandler.getStackInSlot(0), this.craftingMatrixHandler);

    if (recipe != null) {
      this.lastRecipeCrafted = recipe;
      this.resultHandler.setStackInSlot(0, recipe.getResult());

    } else {
      this.resultHandler.setStackInSlot(0, ItemStack.EMPTY);
    }
  }

  protected abstract int getWorkbenchGuiTextShadowColor();

  protected abstract ResourceLocation getBackgroundTexture();

  public abstract RegistryRecipeWorkbenchBasic getRecipeRegistry();

  public enum EnumWorkbenchTier {

    BASIC(0, "basic");

    private final int index;
    private final String name;

    EnumWorkbenchTier(int index, String name) {

      this.index = index;
      this.name = name;
    }

    public String getName() {

      return this.name;
    }

    public int getIndex() {

      return this.index;
    }
  }
}
