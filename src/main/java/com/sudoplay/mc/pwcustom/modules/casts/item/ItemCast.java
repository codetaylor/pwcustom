package com.sudoplay.mc.pwcustom.modules.casts.item;

import com.codetaylor.mc.athenaeum.spi.IVariant;
import com.codetaylor.mc.athenaeum.spi.ItemBase;
import com.sudoplay.mc.pwcustom.modules.casts.ModuleCasts;
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

    super(ModuleCasts.MOD_ID, ModuleCasts.CREATIVE_TAB, NAME);
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

    AXE_HEAD("axe_head", 0),
    PICKAXE_HEAD("pickaxe_head", 1),
    SHOVEL_HEAD("shovel_head", 2),
    HOE_HEAD("hoe_head", 3),
    TOOL_ROD("tool_rod", 4),
    SWORD_BLADE("sword_blade", 5),
    SWORD_GUARD("sword_guard", 6),
    HAMMER_HEAD("hammer_head", 7),
    BOW_LIMB("bow_limb", 8),
    SICKLE_BLADE("sickle_blade", 9),
    ROD("rod", 10),
    SHEAR_BLADES("shear_blades", 11),
    RING("ring", 12),
    PAXEL_HEAD("paxel_head", 13),
    SHIELD("shield", 14);

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
