package com.sudoplay.mc.pwcustom.modules.block.block;

import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;

public class BlockSoulGravel
    extends BlockGravel {

  public static final String NAME = "soul_gravel";

  public BlockSoulGravel() {

    super();
    setHardness(0.6F);
    setSoundType(SoundType.GROUND);
  }

}
