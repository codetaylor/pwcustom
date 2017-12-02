package com.sudoplay.mc.pwcustom.item;

import com.sudoplay.mc.pwcustom.spi.IVariant;

import java.util.Comparator;
import java.util.stream.Stream;

public enum EnumCastType
    implements IVariant {

  SAW_BLADE("saw_blade", 0),
  AXE_HEAD("axe_head", 1),
  PICKAXE_HEAD("pickaxe_head", 2),
  SHOVEL_HEAD("shovel_head", 3),
  HOE_HEAD("hoe_head", 4),
  TOOL_ROD("tool_rod", 5),
  SWORD_BLADE("sword_blade", 6),
  SWORD_GUARD("sword_guard", 7),
  HAMMER_HEAD("hammer_head", 8),
  BOW_LIMB("bow_limb", 9),
  SICKLE_BLADE("sickle_blade", 10),
  ROD("rod", 11),
  SHEAR_BLADES("shear_blades", 12),
  RING("ring", 13),
  PAXEL_HEAD("paxel_head", 14),
  SHIELD("shield", 15);

  private static final EnumCastType[] META_LOOKUP = Stream
      .of(EnumCastType.values())
      .sorted(Comparator.comparing(EnumCastType::getMeta))
      .toArray(EnumCastType[]::new);

  private final String name;
  private final int meta;

  EnumCastType(String name, int meta) {

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

  public static EnumCastType fromMeta(int meta) {

    if (meta < 0 || meta > META_LOOKUP.length) {
      meta = 0;
    }

    return META_LOOKUP[meta];
  }
}
