package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.biome;

import com.sudoplay.mc.pwcustom.modules.dumpt.Util;
import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;

public abstract class SerializerBiomeSpawnableListBase
    implements IElementSerializer<Biome> {

  @Override
  public String serialize(Biome element) {

    return Util.encapsulateQuotes(element.getSpawnableList(this.getCreatureType()).toString());
  }

  protected abstract EnumCreatureType getCreatureType();
}
