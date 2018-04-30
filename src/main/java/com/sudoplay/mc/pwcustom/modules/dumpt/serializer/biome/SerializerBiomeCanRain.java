package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.biome;

import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.world.biome.Biome;

public class SerializerBiomeCanRain
    implements IElementSerializer<Biome> {

  @Override
  public String getTitle() {

    return "CanRain";
  }

  @Override
  public String serialize(Biome element) {

    return String.valueOf(element.canRain());
  }
}
