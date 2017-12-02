package com.sudoplay.mc.pwcustom.item;

import com.sudoplay.mc.pwcustom.spi.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemCast
    extends ItemBase {

  public static final String NAME = "cast";

  public ItemCast() {

    super(NAME);
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    return super.getUnlocalizedName(stack) + "." + EnumCastType.fromMeta(stack.getMetadata()).getName();
  }

  @Override
  public void getSubItems(
      CreativeTabs tab, NonNullList<ItemStack> items
  ) {

    if (this.isInCreativeTab(tab)) {

      List<ItemStack> list = Stream.of(EnumCastType.values())
          .map(enumType -> new ItemStack(this, 1, enumType.getMeta()))
          .collect(Collectors.toList());

      items.addAll(list);
    }
  }
}
