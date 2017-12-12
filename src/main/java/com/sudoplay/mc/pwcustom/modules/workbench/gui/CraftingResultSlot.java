package com.sudoplay.mc.pwcustom.modules.workbench.gui;

import com.sudoplay.mc.pwcustom.modules.workbench.tile.TileEntityWorkbenchBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class CraftingResultSlot
    extends SlotItemHandler {

  private final TileEntityWorkbenchBase tile;

  public CraftingResultSlot(
      TileEntityWorkbenchBase tile,
      IItemHandler itemHandler,
      int index,
      int xPosition,
      int yPosition
  ) {

    super(itemHandler, index, xPosition, yPosition);
    this.tile = tile;
  }

  @Override
  public boolean isItemValid(@Nonnull ItemStack stack) {

    return false;
  }

  @Override
  public ItemStack onTake(
      EntityPlayer player, ItemStack stack
  ) {

    this.tile.onTakeResult(player);
    return stack;
  }
}
