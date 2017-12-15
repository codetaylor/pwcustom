package com.sudoplay.mc.pwcustom.modules.mortar.tile;

import com.sudoplay.mc.pwcustom.modules.mortar.ModuleMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.api.MortarAPI;
import com.sudoplay.mc.pwcustom.modules.mortar.recipe.IRecipeMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.recipe.RecipeMortarCrushing;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class MortarDelegateCrushing
    implements IMortar {

  private ItemStackHandler itemStackHandler;
  private Runnable changeObserver;

  public MortarDelegateCrushing(Runnable changeObserver) {

    this.changeObserver = changeObserver;

    this.itemStackHandler = new ItemStackHandler(1);
  }

  @Override
  public EnumMortarMode getMortarMode() {

    return EnumMortarMode.CRUSHING;
  }

  @Override
  public String getMortarModeString() {

    return ModuleMortar.Lang.MORTAR_MODE_CRUSHING;
  }

  @Override
  public ItemStackHandler getItemStackHandler() {

    return this.itemStackHandler;
  }

  @Override
  public boolean canInsertItem(ItemStack itemStack) {

    ItemStack resultItemStack = this.itemStackHandler.insertItem(0, itemStack, true);
    return resultItemStack.getCount() < itemStack.getCount();
  }

  @Override
  public void insertItem(ItemStack itemStack) {

    if (this.canInsertItem(itemStack)) {
      ItemStack resultItemStack = this.itemStackHandler.insertItem(0, itemStack.copy(), false);
      itemStack.setCount(resultItemStack.getCount());
      this.changeObserver.run();
    }
  }

  @Override
  public ItemStack removeItem() {

    if (this.isEmpty()) {
      return ItemStack.EMPTY;
    }

    ItemStack result = this.itemStackHandler.extractItem(0, this.getItemCount(), false);
    this.changeObserver.run();
    return result;
  }

  @Override
  public int getItemCount() {

    return this.itemStackHandler.getStackInSlot(0).getCount();
  }

  @Override
  public boolean isEmpty() {

    return this.itemStackHandler.getStackInSlot(0).isEmpty();
  }

  @Override
  public NBTTagCompound serializeNBT() {

    return this.itemStackHandler.serializeNBT();
  }

  @Override
  public void deserializeNBT(NBTTagCompound compound) {

    this.itemStackHandler.deserializeNBT(compound);
  }

  @Override
  public IRecipeMortar getRecipe() {

    return MortarAPI.RECIPE_REGISTRY.findCrushingRecipe(this.itemStackHandler.getStackInSlot(0));
  }

  @Override
  public ItemStack doCrafting() {

    RecipeMortarCrushing recipe = (RecipeMortarCrushing) this.getRecipe();
    ItemStack[] matchingStacks = recipe.getInput().getMatchingStacks();
    int count = matchingStacks[0].getCount();
    this.itemStackHandler.getStackInSlot(0).shrink(count);
    this.changeObserver.run();
    return recipe.getOutput();
  }

}
