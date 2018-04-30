package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.biome;

import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.world.biome.Biome;

public class SerializerBiomeName
    implements IElementSerializer<Biome> {

  @Override
  public String getTitle() {

    return "Name";
  }

  @Override
  public String serialize(Biome element) {

    return element.getBiomeName();
  }
}
