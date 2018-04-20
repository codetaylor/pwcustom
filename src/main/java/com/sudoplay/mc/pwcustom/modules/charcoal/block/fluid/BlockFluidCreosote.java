package com.sudoplay.mc.pwcustom.modules.charcoal.block.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidCreosote
    extends BlockFluidClassic {

  public BlockFluidCreosote(Fluid fluid) {

    super(fluid, Material.WATER);
    Blocks.FIRE.setFireInfo(this, 100, 5);
  }

}