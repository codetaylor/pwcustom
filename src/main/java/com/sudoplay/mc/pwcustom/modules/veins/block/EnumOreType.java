package com.sudoplay.mc.pwcustom.modules.veins.block;

import com.codetaylor.mc.athenaeum.spi.IVariant;

import java.util.Comparator;
import java.util.stream.Stream;

public enum EnumOreType
    implements IVariant {

  COPPER(0, "copper"),
  IRON(1, "iron"),
  SILVER(2, "silver"),
  LEAD(3, "lead"),
  PLATINUM(4, "platinum"),
  NICKEL(5, "nickel"),
  TIN(6, "tin"),
  ALUMINUM(7, "aluminum"),
  GOLD(8, "gold"),
  COAL(9, "coal"),
  EMERALD(10, "emerald"),
  DIAMOND(11, "diamond"),
  LAPIS(12, "lapis"),
  REDSTONE(13, "redstone"),
  QUARTZ(14, "quartz"),
  ENDER(15, "ender");

  private static final EnumOreType[] META_LOOKUP = Stream.of(EnumOreType.values())
      .sorted(Comparator.comparing(EnumOreType::getMeta))
      .toArray(EnumOreType[]::new);

  private final int meta;
  private final String name;

  EnumOreType(int meta, String name) {

    this.meta = meta;
    this.name = name;
  }

  @Override
  public int getMeta() {

    return this.meta;
  }

  @Override
  public String getName() {

    return this.name;
  }

  public static EnumOreType fromMeta(int meta) {

    if (meta < 0 || meta >= META_LOOKUP.length) {
      meta = 0;
    }

    return META_LOOKUP[meta];
  }

}
