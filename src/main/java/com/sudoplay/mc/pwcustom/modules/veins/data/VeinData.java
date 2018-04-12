package com.sudoplay.mc.pwcustom.modules.veins.data;

import net.minecraft.block.state.IBlockState;

import java.util.function.Predicate;

public class VeinData {

  public transient Predicate<IBlockState> _toReplace;
  public transient IBlockState _replaceWith;

  public VeinDataDimensions dimensions = new VeinDataDimensions();
  public VeinDataBiomes biomes = new VeinDataBiomes();
  public String toReplace;
  public String replaceWith;
  public int range;
  public int weight;
  public int minGenHeight;
  public int maxGenHeight;
  public float minSize;
  public float maxSize;
  public float length;

}
