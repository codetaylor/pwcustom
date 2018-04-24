package com.sudoplay.mc.pwcustom.library.fluid;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;

/**
 * Derived from:
 * https://github.com/SlimeKnights/TinkersConstruct/blob/master/src/main/java/slimeknights/tconstruct/library/fluid/FluidTankBase.java
 *
 * @param <T>
 */
public class FluidTankBase<T extends TileEntity>
    extends FluidTank {

  protected T parent;

  public FluidTankBase(int capacity, T parent) {

    super(capacity);
    this.parent = parent;
  }

  @Override
  public int fillInternal(FluidStack resource, boolean doFill) {

    int amount = super.fillInternal(resource, doFill);

    if (amount > 0 && doFill) {
      this.onContentsChanged(amount);
    }

    return amount;
  }

  @Nullable
  @Override
  public FluidStack drainInternal(int maxDrain, boolean doDrain) {

    FluidStack fluidStack = super.drainInternal(maxDrain, doDrain);

    if (fluidStack != null && doDrain) {
      this.onContentsChanged(-fluidStack.amount);
    }

    return fluidStack;
  }

  @Override
  public void setCapacity(int capacity) {

    this.capacity = capacity;

    if (this.fluid != null
        && this.fluid.amount > capacity) {
      this.drainInternal(this.fluid.amount - capacity, true);
    }
  }

  protected void onContentsChanged(int amount) {

    if (amount != 0
        && this.parent instanceof IFluidTankUpdateListener) {
      ((IFluidTankUpdateListener) this.parent).onTankContentsChanged(this);
    }
  }
}
