package com.sudoplay.mc.pwcustom.modules.dumpt.serializer.item;

import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class SerializerItemFoodSaturationModifier
    implements IElementSerializer<ItemStack> {

  @Override
  public String getTitle() {

    return "SaturationModifier";
  }

  @Override
  public String serialize(ItemStack element) {

    Item item = element.getItem();

    if (item instanceof ItemFood) {
      return String.valueOf(((ItemFood) item).getSaturationModifier(element));
    }

    return "null";
  }
}
