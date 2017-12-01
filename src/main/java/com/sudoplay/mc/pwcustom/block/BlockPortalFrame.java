package com.sudoplay.mc.pwcustom.block;

import com.sudoplay.mc.pwcustom.item.ItemPortalWand;
import com.sudoplay.mc.pwcustom.spi.BlockBase;
import com.sudoplay.mc.pwcustom.spi.IBlockVariant;
import com.sudoplay.mc.pwcustom.spi.IVariant;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Comparator;
import java.util.stream.Stream;

public class BlockPortalFrame
    extends BlockBase
    implements IBlockVariant<BlockPortalFrame.EnumType> {

  public static final String NAME = "portal_frame";

  public static final IProperty<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);

  public BlockPortalFrame() {

    super(Material.ROCK, NAME);

    this.setHardness(50);
    this.setResistance(2000);
    this.setSoundType(SoundType.STONE);
    this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.DARKLANDS));
  }

  @Override
  protected BlockStateContainer createBlockState() {

    return new BlockStateContainer(this, VARIANT);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {

    return this.getDefaultState().withProperty(VARIANT, EnumType.fromMeta(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {

    return state.getValue(VARIANT).getMeta();
  }

  @Override
  public int damageDropped(IBlockState state) {

    return this.getMetaFromState(state);
  }

  @Override
  public void getSubBlocks(
      CreativeTabs tab,
      NonNullList<ItemStack> list
  ) {

    for (EnumType type : EnumType.values()) {
      list.add(new ItemStack(this, 1, type.getMeta()));
    }
  }

  @Override
  public String getName(ItemStack stack) {

    return EnumType.fromMeta(stack.getMetadata()).getName();
  }

  @Override
  public IProperty<EnumType> getVariant() {

    return VARIANT;
  }

  @Override
  public boolean onBlockActivated(
      World worldIn,
      BlockPos pos,
      IBlockState state,
      EntityPlayer playerIn,
      EnumHand hand,
      EnumFacing facing,
      float hitX,
      float hitY,
      float hitZ
  ) {

    if (worldIn.isRemote) {
      return false;
    }

    ItemStack heldItem = playerIn.getHeldItem(hand);

    if (heldItem.isEmpty()
        || !(heldItem.getItem() instanceof ItemPortalWand)
        || this.getMetaFromState(state) != heldItem.getMetadata()) {
      return false;
    }

    EnumType type = EnumType.fromMeta(heldItem.getMetadata());

    switch (type) {
      /*case FIRELANDS:

        if (playerIn.dimension != 0 && playerIn.dimension != ModConfig.DIMENSION_FIRELANDS_ID) {
          return false;
        }

        for (EnumFacing offset : EnumFacing.VALUES) {

          if (ModBlocks.PORTAL_FIRELANDS.trySpawnPortal(worldIn, new BlockPos(pos).offset(offset))) {
            return true;
          }
        }

        return false;*/

      default:
        throw new NotImplementedException(type.getName());
    }

  }

  public enum EnumType
      implements IVariant {

    DARKLANDS(0, "darklands");

    private static final EnumType[] META_LOOKUP = Stream.of(EnumType.values())
        .sorted(Comparator.comparing(EnumType::getMeta))
        .toArray(EnumType[]::new);

    private final int meta;
    private final String name;

    EnumType(int meta, String name) {

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

    public static EnumType fromMeta(int meta) {

      if (meta < 0 || meta >= META_LOOKUP.length) {
        meta = 0;
      }

      return META_LOOKUP[meta];
    }

  }
}

























