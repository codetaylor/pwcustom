package com.sudoplay.mc.pwcustom.workbench.gui;

import com.sudoplay.mc.pwcustom.init.ModBlocks;
import com.sudoplay.mc.pwcustom.inventory.PredicateSlotItemHandler;
import com.sudoplay.mc.pwcustom.workbench.block.BlockWorkbench;
import com.sudoplay.mc.pwcustom.workbench.tile.TileEntityWorkbenchBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;

import java.util.List;

public class ContainerWorkbenchBasic
    extends Container {

  private World world;
  private TileEntityWorkbenchBase tile;

  public ContainerWorkbenchBasic(
      InventoryPlayer playerInventory,
      World world,
      TileEntityWorkbenchBase tile
  ) {

    this.world = world;
    this.tile = tile;

    // Result Slot 0
    this.addSlotToContainer(new CraftingResultSlot(this.tile, this.tile.getResultHandler(), 0, 124, 35));

    // Crafting Matrix 1 - 9
    for (int y = 0; y < this.tile.getCraftingMatrixHandler().getHeight(); ++y) {
      for (int x = 0; x < this.tile.getCraftingMatrixHandler().getWidth(); ++x) {
        this.addSlotToContainer(new SlotItemHandler(
            this.tile.getCraftingMatrixHandler(),
            x + y * 3,
            29 + x * 18,
            17 + y * 18
        ));
      }
    }

    // Player Inventory 10 - 37
    for (int y = 0; y < 3; ++y) {
      for (int x = 0; x < 9; ++x) {
        this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
      }
    }

    // Player HotBar 38 - 46
    for (int x = 0; x < 9; ++x) {
      this.addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142));
    }

    // Tool Slot 47
    this.addSlotToContainer(new PredicateSlotItemHandler(
        itemStack -> ContainerWorkbenchBasic.this.tile.getRecipeRegistry().containsRecipeWithTool(itemStack),
        this.tile.getToolHandler(),
        0,
        87,
        35
    ));
  }

  @Override
  public void onCraftMatrixChanged(IInventory inventoryIn) {

    //
  }

  @Override
  public boolean canInteractWith(EntityPlayer playerIn) {

    return this.tile.canPlayerUse(playerIn);
  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer playerIn, int slotIndex) {

    ItemStack itemstack = ItemStack.EMPTY;
    Slot slot = this.inventorySlots.get(slotIndex);

    if (slot != null && slot.getHasStack()) {
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();

      if (slotIndex == 0) {
        // Result

        itemstack1.getItem().onCreated(itemstack1, this.world, playerIn);

        if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
          return ItemStack.EMPTY;
        }

        slot.onSlotChange(itemstack1, itemstack);

      } else if (slotIndex >= 10 && slotIndex < 37) {
        // Inventory clicked, try to move to tool slot first, then hotbar

        if (!this.mergeItemStack(itemstack1, 46, 47, false)
            && !this.mergeItemStack(itemstack1, 37, 46, false)) {
          return ItemStack.EMPTY;
        }

      } else if (slotIndex >= 37 && slotIndex < 46) {
        // HotBar clicked, try to move to tool slot first, then inventory

        if (!this.mergeItemStack(itemstack1, 46, 47, false)
            && !this.mergeItemStack(itemstack1, 10, 37, false)) {
          return ItemStack.EMPTY;
        }

      } else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
        // All others: tool slot and crafting matrix
        return ItemStack.EMPTY;
      }

      if (itemstack1.isEmpty()) {
        slot.putStack(ItemStack.EMPTY);

      } else {
        slot.onSlotChanged();
      }

      if (itemstack1.getCount() == itemstack.getCount()) {
        return ItemStack.EMPTY;
      }

      ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);

      if (slotIndex == 0) {
        playerIn.dropItem(itemstack2, false);
      }
    }

    return itemstack;
  }

  public List<Slot> getRecipeSlots(List<Slot> result) {

    // grid
    for (int i = 1; i < 10; i++) {
      result.add(this.inventorySlots.get(i));
    }

    // tool
    result.add(this.inventorySlots.get(this.inventorySlots.size() - 1));

    return result;
  }

  public List<Slot> getInventorySlots(List<Slot> result) {

    for (int i = 10; i < 46; i++) {
      result.add(this.inventorySlots.get(i));
    }

    return result;
  }

  public TileEntityWorkbenchBase getTile() {

    return this.tile;
  }
}
