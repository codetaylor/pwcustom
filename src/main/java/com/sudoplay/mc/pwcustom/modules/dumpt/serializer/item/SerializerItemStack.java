package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.item;

import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.item.ItemStack;

public class SerializerItemStack
    implements IElementSerializer<ItemStack> {

  @Override
  public String getTitle() {

    return "ItemStack";
  }

  @Override
  public String serialize(ItemStack stack) {

    return "<" + stack.getItem().getRegistryName() + ":" + stack.getMetadata() + ">";
  }

}
