package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.biome;

import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.world.biome.Biome;

public class SerializerBiomeEnableSnow
    implements IElementSerializer<Biome> {

  @Override
  public String getTitle() {

    return "EnableSnow";
  }

  @Override
  public String serialize(Biome element) {

    return String.valueOf(element.getEnableSnow());
  }
}
