package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.biome;

import net.minecraft.entity.EnumCreatureType;

public class SerializerBiomeSpawnableListMonster
    extends SerializerBiomeSpawnableListBase {

  @Override
  public String getTitle() {

    return "SpawnableList:Monster";
  }

  @Override
  protected EnumCreatureType getCreatureType() {

    return EnumCreatureType.MONSTER;
  }
}
