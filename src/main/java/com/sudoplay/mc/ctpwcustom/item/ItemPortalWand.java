package com.sudoplay.mc.ctpwcustom.item;

import com.sudoplay.mc.ctpwcustom.block.BlockPortalFrame;
import com.sudoplay.mc.ctpwcustom.spi.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemPortalWand
    extends ItemBase {

  public static final String NAME = "portal_wand";

  public ItemPortalWand() {

    super(NAME);
    this.setMaxStackSize(1);
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    return super.getUnlocalizedName(stack) + "." + BlockPortalFrame.EnumType.fromMeta(stack.getMetadata()).getName();
  }

  @Override
  public void getSubItems(
      CreativeTabs tab,
      NonNullList<ItemStack> items
  ) {

    if (this.isInCreativeTab(tab)) {

      List<ItemStack> list = Stream.of(BlockPortalFrame.EnumType.values())
          .map(enumType -> new ItemStack(this, 1, enumType.getMeta()))
          .collect(Collectors.toList());

      items.addAll(list);
    }

  }

}
