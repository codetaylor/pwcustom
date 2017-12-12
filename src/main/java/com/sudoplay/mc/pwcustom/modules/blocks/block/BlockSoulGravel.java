package com.sudoplay.mc.pwcustom.modules.blocks.block;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;

public class BlockSoulGravel
    extends BlockGravel {

  public static final String NAME = "soul_gravel";

  public BlockSoulGravel() {

    super();
    setRegistryName(ModPWCustom.MOD_ID, NAME);
    setUnlocalizedName(ModPWCustom.MOD_ID + "." + NAME);
    setCreativeTab(ModPWCustom.CREATIVE_TAB);
    setHardness(0.6F);
    setSoundType(SoundType.GROUND);
  }

}
