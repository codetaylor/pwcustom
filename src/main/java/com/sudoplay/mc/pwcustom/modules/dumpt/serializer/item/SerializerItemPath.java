package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.item;

import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SerializerItemPath
    implements IElementSerializer<ItemStack> {

  @Override
  public String getTitle() {

    return "Path";
  }

  @Override
  public String serialize(ItemStack element) {

    ResourceLocation registryName = element.getItem().getRegistryName();

    if (registryName != null) {
      return registryName.getResourcePath();
    }

    return "null";
  }
}
