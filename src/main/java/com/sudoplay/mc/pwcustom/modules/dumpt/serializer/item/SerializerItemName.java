package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.item;

import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.item.ItemStack;

public class SerializerItemName
    implements IElementSerializer<ItemStack> {

  @Override
  public String getTitle() {

    return "Name";
  }

  @Override
  public String serialize(ItemStack stack) {

    return stack.getItem().getItemStackDisplayName(stack);
  }
}
