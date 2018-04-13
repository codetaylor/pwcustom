package com.sudoplay.mc.pwcustom.modules.veins.data;

import net.minecraft.block.state.IBlockState;

import java.util.function.Predicate;

public class VeinData {

  public transient Predicate<IBlockState> _toReplace;
  public transient IBlockState _replaceWith;
  public transient IBlockState _indicator;

  public VeinDataDimensions dimensions = new VeinDataDimensions();
  public VeinDataBiomes biomes = new VeinDataBiomes();
  public String toReplace = "minecraft:stone:*";
  public String replaceWith = "minecraft:dirt";
  public String indicator = "minecraft:gravel";
  public boolean generateIndicator = true;
  public int range = 4;
  public int weight = 100;
  public int minGenHeight = 0;
  public int maxGenHeight = 50;
  public float minSize = 3;
  public float maxSize = 5;
  public float length = 100;

}
