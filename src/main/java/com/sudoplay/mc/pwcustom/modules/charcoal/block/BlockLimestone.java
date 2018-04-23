package com.sudoplay.mc.pwcustom.modules.charcoal.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockLimestone
    extends Block {

  public static final String NAME = "limestone";

  public BlockLimestone() {

    super(Material.ROCK);
    this.setSoundType(SoundType.STONE);
    this.setHardness(1.5f);
    this.setResistance(10);
    this.setHarvestLevel("pickaxe", 0);
  }

}
