package com.sudoplay.mc.pwcustom.modules.charcoal.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class BlockThatch
    extends Block {

  public static final String NAME = "thatch";

  public BlockThatch() {

    super(Material.GRASS);
    this.setSoundType(SoundType.PLANT);
    this.setHardness(0.5F);
    this.setHarvestLevel("none", 0);
    Blocks.FIRE.setFireInfo(this, 60, 100);
  }

}
