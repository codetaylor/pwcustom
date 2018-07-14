package com.sudoplay.mc.pwcustom.modules.veins.block;

import com.codetaylor.mc.athenaeum.spi.IBlockVariant;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockGravelOre
    extends BlockFalling
    implements IBlockVariant<EnumOreType> {

  public static final IProperty<EnumOreType> VARIANT = PropertyEnum.create("variant", EnumOreType.class);

  public BlockGravelOre() {

    super(Material.ROCK);
    this.setHardness(0.6F);
    this.setSoundType(SoundType.GROUND);
    this.setHarvestLevel("shovel", 0);
    this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumOreType.IRON));
  }

  public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {

    return MapColor.STONE;
  }

  @SideOnly(Side.CLIENT)
  public int getDustColor(IBlockState state) {

    return -8356741;
  }

  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getBlockLayer() {

    return BlockRenderLayer.CUTOUT_MIPPED;
  }

  @Override
  protected BlockStateContainer createBlockState() {

    return new BlockStateContainer(this, VARIANT);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {

    return this.getDefaultState().withProperty(VARIANT, EnumOreType.fromMeta(meta));
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

    for (EnumOreType type : EnumOreType.values()) {
      list.add(new ItemStack(this, 1, type.getMeta()));
    }
  }

  @Nonnull
  @Override
  public String getModelName(ItemStack stack) {

    return EnumOreType.fromMeta(stack.getMetadata()).getName();
  }

  @Nonnull
  @Override
  public IProperty<EnumOreType> getVariant() {

    return VARIANT;
  }

}
