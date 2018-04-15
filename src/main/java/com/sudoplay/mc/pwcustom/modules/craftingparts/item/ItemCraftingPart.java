package com.sudoplay.mc.pwcustom.modules.craftingparts.item;

import com.codetaylor.mc.athenaeum.spi.IVariant;
import com.codetaylor.mc.athenaeum.spi.ItemBase;
import com.sudoplay.mc.pwcustom.modules.craftingparts.ModuleCraftingParts;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemCraftingPart
    extends ItemBase {

  public static final String NAME = "crafting_part";

  public ItemCraftingPart() {

    super(ModuleCraftingParts.MOD_ID, ModuleCraftingParts.CREATIVE_TAB, NAME);
    this.setHasSubtypes(true);
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    return super.getUnlocalizedName(stack) + "." + EnumType.fromMeta(stack.getMetadata()).getName();
  }

  @Override
  public void getSubItems(
      CreativeTabs tab, NonNullList<ItemStack> items
  ) {

    if (this.isInCreativeTab(tab)) {

      List<ItemStack> list = Stream.of(EnumType.values())
          .map(enumType -> new ItemStack(this, 1, enumType.getMeta()))
          .collect(Collectors.toList());

      items.addAll(list);
    }
  }

  public enum EnumType
      implements IVariant {

    TANNED_LEATHER_CORD("tanned_leather_cord", 0),
    TANNED_LEATHER_STRIPS("tanned_leather_strips", 1),
    BOW_LIMB_WOODEN("bow_limb_wooden", 2),
    CRAFTING_RUNE_COMMON("crafting_rune_common", 3),
    CRAFTING_RUNE_UNCOMMON("crafting_rune_uncommon", 4),
    CRAFTING_RUNE_RARE("crafting_rune_rare", 5),
    CRAFTING_RUNE_EPIC("crafting_rune_epic", 6),
    CRAFTING_RUNE_LEGENDARY("crafting_rune_legendary", 7),
    PAPER_GOLD_LEAF("paper_gold_leaf", 8),
    TANNED_LEATHER_SHEET("tanned_leather_sheet", 9),
    LEATHER_STRIPS("leather_strips", 10),
    TANNED_LEATHER_STRIP("tanned_leather_strip", 11),
    LEATHER_CORD("leather_cord", 12),
    HARDENED_LEATHER_SHEET("hardened_leather_sheet", 13),
    HARDENED_LEATHER_STRIPS("hardened_leather_strips", 14),
    HARDENED_LEATHER_STRIP("hardened_leather_strip", 15),
    HARDENED_LEATHER_CORD("hardened_leather_cord", 16),
    ;

    private static final EnumType[] META_LOOKUP = Stream
        .of(EnumType.values())
        .sorted(Comparator.comparing(EnumType::getMeta))
        .toArray(EnumType[]::new);

    private final String name;
    private final int meta;

    EnumType(String name, int meta) {

      this.name = name;
      this.meta = meta;
    }

    @Override
    public int getMeta() {

      return this.meta;
    }

    @Override
    public String getName() {

      return this.name;
    }

    public static EnumType fromMeta(int meta) {

      if (meta < 0 || meta > META_LOOKUP.length) {
        meta = 0;
      }

      return META_LOOKUP[meta];
    }
  }
}