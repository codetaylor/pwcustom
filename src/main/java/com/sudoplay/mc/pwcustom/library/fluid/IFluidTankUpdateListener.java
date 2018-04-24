package com.sudoplay.mc.pwcustom.library.fluid;

import net.minecraftforge.fluids.FluidTank;

public interface IFluidTankUpdateListener {

  void onTankContentsChanged(FluidTank fluidTank);
}
