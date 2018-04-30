package com.sudoplay.mc.pwcustom.modules.dumpt.serializer;

import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class SerializerForgeRegistryEntryPath
    implements IElementSerializer<IForgeRegistryEntry> {

  @Override
  public String getTitle() {

    return "Path";
  }

  @Override
  public String serialize(IForgeRegistryEntry element) {

    ResourceLocation registryName = element.getRegistryName();

    if (registryName != null) {
      return registryName.getResourcePath();
    }

    return "null";
  }
}
