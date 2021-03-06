package com.sudoplay.mc.pwcustom.modules.portals.item;

import com.sudoplay.mc.pwcustom.modules.portals.block.BlockPortalFrame;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemPortalWand
    extends Item {

  public static final String NAME = "portal_wand";

  public ItemPortalWand() {

    this.setMaxStackSize(1);
    this.setHasSubtypes(true);
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
