package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.biome;

import net.minecraft.entity.EnumCreatureType;

public class SerializerBiomeSpawnableListCreature
    extends SerializerBiomeSpawnableListBase {

  @Override
  public String getTitle() {

    return "SpawnableList:Creature";
  }

  @Override
  protected EnumCreatureType getCreatureType() {

    return EnumCreatureType.CREATURE;
  }
}
