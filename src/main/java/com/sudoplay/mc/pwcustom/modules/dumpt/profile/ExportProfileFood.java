package com.sudoplay.mc.pwcustom.modules.dumpt.profile;

import com.sudoplay.mc.pwcustom.modules.dumpt.serializer.item.*;
import com.sudoplay.mc.pwcustom.modules.dumpt.spi.profile.ExportProfileItemBase;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.function.Predicate;

public class ExportProfileFood
    extends ExportProfileItemBase {

  public ExportProfileFood() {

    super(Arrays.asList(
        new SerializerItemName(),
        new SerializerItemDomain(),
        new SerializerItemPath(),
        new SerializerItemStack(),
        new SerializerItemFoodHealAmount(),
        new SerializerItemFoodSaturationModifier(),
        new SerializerItemStackSize()
    ));
  }

  @Override
  public String getFilename() {

    return "food.csv";
  }

  @Override
  protected Predicate<ItemStack> getFilter() {

    return itemStack -> itemStack.getItem() instanceof ItemFood;
  }

}
