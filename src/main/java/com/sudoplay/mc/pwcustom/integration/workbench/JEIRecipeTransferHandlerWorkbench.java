package com.sudoplay.mc.pwcustom.integration.workbench;

import com.sudoplay.mc.pwcustom.workbench.gui.ContainerWorkbenchBasic;
import mezz.jei.Internal;
import mezz.jei.JustEnoughItems;
import mezz.jei.api.gui.IGuiIngredient;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import mezz.jei.config.SessionData;
import mezz.jei.network.packets.PacketRecipeTransfer;
import mezz.jei.startup.StackHelper;
import mezz.jei.transfer.RecipeTransferErrorInternal;
import mezz.jei.transfer.RecipeTransferErrorSlots;
import mezz.jei.transfer.RecipeTransferErrorTooltip;
import mezz.jei.util.Log;
import mezz.jei.util.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.*;

public class JEIRecipeTransferHandlerWorkbench
    implements IRecipeTransferHandler<ContainerWorkbenchBasic> {

  private IRecipeTransferInfo<ContainerWorkbenchBasic> info;

  public JEIRecipeTransferHandlerWorkbench(IRecipeTransferInfo<ContainerWorkbenchBasic> info) {

    this.info = info;
  }

  @Override
  public Class<ContainerWorkbenchBasic> getContainerClass() {

    return ContainerWorkbenchBasic.class;
  }

  @Nullable
  @Override
  public IRecipeTransferError transferRecipe(
      ContainerWorkbenchBasic container,
      IRecipeLayout recipeLayout,
      EntityPlayer player,
      boolean maxTransfer,
      boolean doTransfer
  ) {

    if (!SessionData.isJeiOnServer()) {
      String tooltipMessage = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.no.server");
      return new RecipeTransferErrorTooltip(tooltipMessage);
    }

    if (!this.info.canHandle(container)) {
      return RecipeTransferErrorInternal.INSTANCE;
    }

    Map<Integer, Slot> inventorySlots = new HashMap<>();
    for (Slot slot : this.info.getInventorySlots(container)) {
      inventorySlots.put(slot.slotNumber, slot);
    }

    Map<Integer, Slot> craftingSlots = new HashMap<>();
    for (Slot slot : this.info.getRecipeSlots(container)) {
      craftingSlots.put(slot.slotNumber, slot);
    }

    int inputCount = 0;
    IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
    for (IGuiIngredient<ItemStack> ingredient : itemStackGroup.getGuiIngredients().values()) {
      if (ingredient.isInput() && !ingredient.getAllIngredients().isEmpty()) {
        inputCount++;
      }
    }

    if (inputCount > craftingSlots.size()) {
      Log.get().error(
          "Recipe Transfer helper {} does not work for container {}",
          this.info.getClass(),
          container.getClass()
      );
      return RecipeTransferErrorInternal.INSTANCE;
    }

    Map<Integer, ItemStack> availableItemStacks = new HashMap<>();
    int filledCraftSlotCount = 0;
    int emptySlotCount = 0;

    for (Slot slot : craftingSlots.values()) {
      final ItemStack stack = slot.getStack();
      if (!stack.isEmpty()) {
        if (!slot.canTakeStack(player)) {
          Log.get().error(
              "Recipe Transfer helper {} does not work for container {}. Player can't move item out of Crafting Slot number {}",
              this.info.getClass(),
              container.getClass(),
              slot.slotNumber
          );
          return RecipeTransferErrorInternal.INSTANCE;
        }
        filledCraftSlotCount++;
        availableItemStacks.put(slot.slotNumber, stack.copy());
      }
    }

    for (Slot slot : inventorySlots.values()) {
      final ItemStack stack = slot.getStack();
      if (!stack.isEmpty()) {
        availableItemStacks.put(slot.slotNumber, stack.copy());
      } else {
        emptySlotCount++;
      }
    }

    // check if we have enough inventory space to shuffle items around to their final locations
    if (filledCraftSlotCount - inputCount > emptySlotCount) {
      String message = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.inventory.full");
      return new RecipeTransferErrorTooltip(message);
    }

    StackHelper.MatchingItemsResult matchingItemsResult = Internal.getStackHelper().getMatchingItems(
        availableItemStacks,
        itemStackGroup.getGuiIngredients()
    );

    if (matchingItemsResult.missingItems.size() > 0) {
      String message = Translator.translateToLocal("jei.tooltip.error.recipe.transfer.missing");
      return new RecipeTransferErrorSlots(message, matchingItemsResult.missingItems);
    }

    List<Integer> craftingSlotIndexes = new ArrayList<>(craftingSlots.keySet());
    Collections.sort(craftingSlotIndexes);

    List<Integer> inventorySlotIndexes = new ArrayList<>(inventorySlots.keySet());
    Collections.sort(inventorySlotIndexes);

    // check that the slots exist and can be altered
    for (Map.Entry<Integer, Integer> entry : matchingItemsResult.matchingItems.entrySet()) {
      int craftNumber = entry.getKey();
      int slotNumber = craftingSlotIndexes.get(craftNumber);

      if (slotNumber < 0 || slotNumber >= container.inventorySlots.size()) {
        Log.get().error(
            "Recipes Transfer Helper {} references slot {} outside of the inventory's size {}",
            this.info.getClass(),
            slotNumber,
            container.inventorySlots.size()
        );
        return RecipeTransferErrorInternal.INSTANCE;
      }
    }

    if (doTransfer) {
      PacketRecipeTransfer packet = new PacketRecipeTransfer(
          matchingItemsResult.matchingItems,
          craftingSlotIndexes,
          inventorySlotIndexes,
          maxTransfer
      );
      JustEnoughItems.getProxy().sendPacketToServer(packet);
    }

    return null;
  }

}
