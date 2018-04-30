package com.sudoplay.mc.pwcustom.modules.dumpt.profile;

import com.sudoplay.mc.pwcustom.modules.dumpt.serializer.item.*;
import com.sudoplay.mc.pwcustom.modules.dumpt.spi.profile.ExportProfileItemBase;
import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

import java.util.function.Predicate;

public class ExportProfileFood
    extends ExportProfileItemBase {

  public ExportProfileFood() {

    //noinspection unchecked
    super(Arrays.asList(new IElementSerializer[]{
        new SerializerItemName(),
        new SerializerItemDomain(),
        new SerializerItemPath(),
        new SerializerItemStack(),
        new SerializerItemFoodHealAmount(),
        new SerializerItemFoodSaturationModifier(),
        new SerializerItemStackSize()
    }));
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
