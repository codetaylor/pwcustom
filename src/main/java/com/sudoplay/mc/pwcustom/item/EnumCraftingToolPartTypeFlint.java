package com.sudoplay.mc.pwcustom.item;

import com.sudoplay.mc.pwcustom.spi.IVariant;

import java.util.Comparator;
import java.util.stream.Stream;

public enum EnumCraftingToolPartTypeFlint
    implements IVariant {

  SAW_BLADE("saw_blade", 0),
  AXE_HEAD("axe_head", 1),
  PICKAXE_HEAD("pickaxe_head", 2),
  SHOVEL_HEAD("shovel_head", 3),
  HOE_HEAD("hoe_head", 4),
  TOOL_ROD("tool_rod", 5),
  SWORD_BLADE("sword_blade", 6),
  SWORD_GUARD("sword_guard", 7),
  WRAPPED_TOOL_ROD("wrapped_tool_rod", 8);

  private static final EnumCraftingToolPartTypeFlint[] META_LOOKUP = Stream
      .of(EnumCraftingToolPartTypeFlint.values())
      .sorted(Comparator.comparing(EnumCraftingToolPartTypeFlint::getMeta))
      .toArray(EnumCraftingToolPartTypeFlint[]::new);

  private final String name;
  private final int meta;

  EnumCraftingToolPartTypeFlint(String name, int meta) {

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

  public static EnumCraftingToolPartTypeFlint fromMeta(int meta) {

    if (meta < 0 || meta > META_LOOKUP.length) {
      meta = 0;
    }

    return META_LOOKUP[meta];
  }
}
