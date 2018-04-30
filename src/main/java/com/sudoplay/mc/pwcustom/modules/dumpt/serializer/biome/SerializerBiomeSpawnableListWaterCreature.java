package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.biome;

import net.minecraft.entity.EnumCreatureType;

public class SerializerBiomeSpawnableListWaterCreature
    extends SerializerBiomeSpawnableListBase {

  @Override
  public String getTitle() {

    return "SpawnableList:WaterCreature";
  }

  @Override
  protected EnumCreatureType getCreatureType() {

    return EnumCreatureType.WATER_CREATURE;
  }
}
