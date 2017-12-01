package com.sudoplay.mc.pwcustom.item;

import com.sudoplay.mc.pwcustom.spi.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemCraftingToolPart
    extends ItemBase {

  public static final String NAME_SAW_BLADE = "crafting_tool_part_saw_blade";
  public static final String NAME_AXE_HEAD = "crafting_tool_part_axe_head";
  public static final String NAME_PICKAXE_HEAD = "crafting_tool_part_pickaxe_head";
  public static final String NAME_SHOVEL_HEAD = "crafting_tool_part_shovel_head";
  public static final String NAME_HOE_HEAD = "crafting_tool_part_hoe_head";

  public ItemCraftingToolPart(String name) {

    super(name);
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    return super.getUnlocalizedName(stack) + "." + EnumCraftingMaterialType.fromMeta(stack.getMetadata()).getName();
  }

  @Override
  public void getSubItems(
      CreativeTabs tab, NonNullList<ItemStack> items
  ) {

    if (this.isInCreativeTab(tab)) {

      List<ItemStack> list = Stream.of(EnumCraftingMaterialType.values())
          .map(enumType -> new ItemStack(this, 1, enumType.getMeta()))
          .collect(Collectors.toList());

      items.addAll(list);
    }
  }
}
