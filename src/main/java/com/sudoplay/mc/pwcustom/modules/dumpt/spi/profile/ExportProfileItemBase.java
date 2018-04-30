package com.sudoplay.mc.pwcustom.modules.dumpt.spi.profile;

import com.sudoplay.mc.pwcustom.modules.dumpt.spi.line.ILineWriter;
import com.sudoplay.mc.pwcustom.modules.dumpt.spi.serializer.IElementSerializer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.function.Predicate;

public abstract class ExportProfileItemBase
    extends ExportProfileBase<ItemStack> {

  public ExportProfileItemBase(List<IElementSerializer<ItemStack>> elementSerializerList) {

    super(elementSerializerList);
  }

  public int write(Writer writer) throws IOException {

    Predicate<ItemStack> filter = this.getFilter();
    ILineWriter<ItemStack> lineWriter = this.getLineWriter();
    int elementCount = 0;

    for (Item item : ForgeRegistries.ITEMS.getValuesCollection()) {

      if (item.getHasSubtypes()) {
        NonNullList<ItemStack> subItemList = NonNullList.create();
        item.getSubItems(CreativeTabs.SEARCH, subItemList);

        for (ItemStack itemStack : subItemList) {

          if (filter.test(itemStack)) {
            lineWriter.write(writer, itemStack);
            elementCount += 1;
          }
        }

      } else {
        ItemStack itemStack = new ItemStack(item);

        if (filter.test(itemStack)) {
          lineWriter.write(writer, itemStack);
          elementCount += 1;
        }
      }
    }

    return elementCount;
  }

  protected abstract Predicate<ItemStack> getFilter();
}
