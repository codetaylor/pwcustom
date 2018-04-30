package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.biome;

import net.minecraft.entity.EnumCreatureType;

public class SerializerBiomeSpawnableListAmbient
    extends SerializerBiomeSpawnableListBase {

  @Override
  public String getTitle() {

    return "SpawnableList:CreatureAmbient";
  }

  @Override
  protected EnumCreatureType getCreatureType() {

    return EnumCreatureType.AMBIENT;
  }

}
