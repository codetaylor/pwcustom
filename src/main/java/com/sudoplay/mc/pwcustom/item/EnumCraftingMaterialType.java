package com.sudoplay.mc.pwcustom.item;

import com.sudoplay.mc.pwcustom.spi.IVariant;

import java.util.Comparator;
import java.util.stream.Stream;

public enum EnumCraftingMaterialType
    implements IVariant {

  FLINT("flint", 0),
  IRON("iron", 1),
  GOLD("gold", 2),
  DIAMOND("diamond", 3),
  ALUMINUM("aluminum", 4),
  BRONZE("bronze", 5),
  CONSTANTAN("constantan", 6),
  COPPER("copper", 7),
  ELECTRUM("electrum", 8),
  INVAR("invar", 9),
  LEAD("lead", 10),
  NICKEL("nickel", 11),
  PLATINUM("platinum", 12),
  SILVER("silver", 13),
  STEEL("steel", 14),
  TIN("tin", 15);

  private static final EnumCraftingMaterialType[] META_LOOKUP = Stream
      .of(EnumCraftingMaterialType.values())
      .sorted(Comparator.comparing(EnumCraftingMaterialType::getMeta))
      .toArray(EnumCraftingMaterialType[]::new);

  private final String name;
  private final int meta;

  EnumCraftingMaterialType(String name, int meta) {

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

  public static EnumCraftingMaterialType fromMeta(int meta) {

    if (meta < 0 || meta > META_LOOKUP.length) {
      meta = 0;
    }

    return META_LOOKUP[meta];
  }
}
