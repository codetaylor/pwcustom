package com.sudoplay.mc.pwcustom.modules.charcoal.init;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelHandler
    implements IFuelHandler {

  @Override
  public int getBurnTime(ItemStack fuel) {

    if (fuel.getItem() == ModuleCharcoal.Items.COAL_COKE) {
      return 3200;

    } else if (fuel.getItem() == Item.getItemFromBlock(ModuleCharcoal.Blocks.COAL_COKE_BLOCK)) {
      return 3200 * 10;

    } else if (this.isFluidBucket(fuel, ModuleFluids.WOOD_TAR.getName())) {
      return 4800;

    } else if (this.isFluidBucket(fuel, ModuleFluids.COAL_TAR.getName())) {
      return 6400;

    } else if (fuel.getItem() == Item.getItemFromBlock(ModuleCharcoal.Blocks.THATCH)) {
      return 200;

    } else if (fuel.getItem() == ModuleCharcoal.Items.STRAW) {
      return 50;
    }

    return 0;
  }

  private boolean isFluidBucket(ItemStack fuel, String name) {

    FluidStack fluidStack = FluidRegistry.getFluidStack(name, 1000);

    if (fluidStack == null) {
      return false;
    }

    FluidStack candidate = FluidStack.loadFluidStackFromNBT(fuel.getTagCompound());
    return fluidStack.isFluidStackIdentical(candidate);
  }
}
