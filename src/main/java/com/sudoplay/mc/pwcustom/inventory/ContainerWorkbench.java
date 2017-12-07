package com.sudoplay.mc.pwcustom.inventory;

import com.sudoplay.mc.pwcustom.tile.TileEntityWorkbenchBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerWorkbench
    extends Container {

  private World world;
  private TileEntityWorkbenchBase tile;

  public ContainerWorkbench(
      InventoryPlayer playerInventory,
      World world,
      TileEntityWorkbenchBase tile
  ) {

    this.world = world;
    this.tile = tile;

    // Result Slot
    this.addSlotToContainer(new CraftingResultSlot(this.tile, this.tile.getResultHandler(), 0, 124, 35));

    // Crafting Matrix
    for (int y = 0; y < this.tile.getCraftingMatrixHandler().getHeight(); ++y) {
      for (int x = 0; x < this.tile.getCraftingMatrixHandler().getWidth(); ++x) {
        this.addSlotToContainer(new SlotItemHandler(
            this.tile.getCraftingMatrixHandler(),
            x + y * 3,
            30 + x * 18,
            17 + y * 18
        ));
      }
    }

    // Player Inventory
    for (int y = 0; y < 3; ++y) {
      for (int x = 0; x < 9; ++x) {
        this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
      }
    }

    // Player HotBar
    for (int x = 0; x < 9; ++x) {
      this.addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142));
    }

    // Tool Slot
    this.addSlotToContainer(new CraftingToolSlot(this.tile.getToolHandler(), 0, 8, 35));
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

      if (slotIndex == 0) { // Result
        itemstack1.getItem().onCreated(itemstack1, this.world, playerIn);

        if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
          return ItemStack.EMPTY;
        }

        slot.onSlotChange(itemstack1, itemstack);

      } else if (slotIndex >= 10 && slotIndex < 37) { // Inventory

        if (!this.mergeItemStack(itemstack1, 37, 46, false)) {
          return ItemStack.EMPTY;
        }

      } else if (slotIndex >= 37 && slotIndex < 46) { // HotBar

        if (!this.mergeItemStack(itemstack1, 10, 37, false)) {
          return ItemStack.EMPTY;
        }

      } else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
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
}
