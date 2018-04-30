package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.item;

import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.item.ItemStack;

public class SerializerItemStackSize
    implements IElementSerializer<ItemStack> {

  @Override
  public String getTitle() {

    return "StackSize";
  }

  @Override
  public String serialize(ItemStack element) {

    return String.valueOf(element.getMaxStackSize());
  }
}
