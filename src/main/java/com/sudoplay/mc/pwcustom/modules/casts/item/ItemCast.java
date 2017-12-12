package com.sudoplay.mc.pwcustom.modules.casts.item;

import com.sudoplay.mc.pwcustom.lib.spi.IVariant;
import com.sudoplay.mc.pwcustom.lib.spi.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemCast
    extends ItemBase {

  public static final String NAME = "cast";

  public ItemCast() {

    super(NAME);
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
