package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.item;

import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SerializerItemDomain
    implements IElementSerializer<ItemStack> {

  @Override
  public String getTitle() {

    return "Domain";
  }

  @Override
  public String serialize(ItemStack element) {

    ResourceLocation registryName = element.getItem().getRegistryName();

    if (registryName != null) {
      return registryName.getResourceDomain();
    }

    return "null";
  }
}
