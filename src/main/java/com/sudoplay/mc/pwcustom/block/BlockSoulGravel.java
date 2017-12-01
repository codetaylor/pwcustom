package com.sudoplay.mc.pwcustom.block;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

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

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {

    if (fortune > 3) {
      fortune = 3;
    }

    return rand.nextInt(10 - fortune * 3) == 0 ? Items.NETHER_WART : super.getItemDropped(state, rand, fortune);
  }

}
