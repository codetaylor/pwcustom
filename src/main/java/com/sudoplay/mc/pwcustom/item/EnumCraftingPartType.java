package com.sudoplay.mc.pwcustom.item;

import com.sudoplay.mc.pwcustom.spi.IVariant;

import java.util.Comparator;
import java.util.stream.Stream;

public enum EnumCraftingPartType
    implements IVariant {

  SAW_HANDLE("saw_handle", 0),
  TANNED_LEATHER_CORD("tanned_leather_cord", 1),
  TANNED_LEATHER_STRIPS("tanned_leather_strips", 2),
  BOW_LIMB_WOODEN("bow_limb_wooden", 3),
  CRAFTING_RUNE_COMMON("crafting_rune_common", 4),
  CRAFTING_RUNE_UNCOMMON("crafting_rune_uncommon", 5),
  CRAFTING_RUNE_RARE("crafting_rune_rare", 6),
  CRAFTING_RUNE_EPIC("crafting_rune_epic", 7),
  CRAFTING_RUNE_LEGENDARY("crafting_rune_legendary", 8),
  PAPER_GOLD_LEAF("paper_gold_leaf", 9);

  private static final EnumCraftingPartType[] META_LOOKUP = Stream
      .of(EnumCraftingPartType.values())
      .sorted(Comparator.comparing(EnumCraftingPartType::getMeta))
      .toArray(EnumCraftingPartType[]::new);

  private final String name;
  private final int meta;

  EnumCraftingPartType(String name, int meta) {

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

  public static EnumCraftingPartType fromMeta(int meta) {

    if (meta < 0 || meta > META_LOOKUP.length) {
      meta = 0;
    }

    return META_LOOKUP[meta];
  }
}
