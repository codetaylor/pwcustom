package com.sudoplay.mc.pwcustom.modules.veins.block;

import com.codetaylor.mc.athenaeum.spi.IBlockVariant;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockDenseOreStone
    extends Block
    implements IBlockVariant<EnumOreType> {

  public static final IProperty<EnumOreType> VARIANT = PropertyEnum.create("variant", EnumOreType.class);

  public BlockDenseOreStone() {

    super(Material.ROCK);
    this.setHardness(3.0F);
    this.setResistance(5.0F);
    this.setSoundType(SoundType.STONE);
    this.setHarvestLevel("pickaxe", 4);
    this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumOreType.IRON));
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
