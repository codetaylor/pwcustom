package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.biome;

import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.world.biome.Biome;

public class SerializerBiomeDefaultTemperature
    implements IElementSerializer<Biome> {

  @Override
  public String getTitle() {

    return "DefaultTemperature";
  }

  @Override
  public String serialize(Biome element) {

    return String.valueOf(element.getDefaultTemperature());
  }
}
