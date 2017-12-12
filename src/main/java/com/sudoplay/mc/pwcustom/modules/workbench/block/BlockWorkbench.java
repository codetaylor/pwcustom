package com.sudoplay.mc.pwcustom.modules.workbench.block;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.lib.spi.BlockBase;
import com.sudoplay.mc.pwcustom.lib.spi.IBlockVariant;
import com.sudoplay.mc.pwcustom.lib.spi.IVariant;
import com.sudoplay.mc.pwcustom.lib.tile.IContainer;
import com.sudoplay.mc.pwcustom.modules.workbench.tile.*;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class BlockWorkbench
    extends BlockBase
    implements IBlockVariant<BlockWorkbench.EnumType> {

  public static final String NAME = "workbench_basic";

  public static final IProperty<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);

  public BlockWorkbench() {

    super(Material.WOOD, NAME);

    this.setHardness(5);
    this.setSoundType(SoundType.WOOD);
    this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.TAILOR));
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {

    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {

    switch (state.getValue(VARIANT)) {
      case TAILOR:
        return new TileEntityWorkbenchTailor();
      case MASON:
        return new TileEntityWorkbenchMason();
      case JEWELER:
        return new TileEntityWorkbenchJeweler();
      case CARPENTER:
        return new TileEntityWorkbenchCarpenter();
      case BLACKSMITH:
        return new TileEntityWorkbenchBlacksmith();

      default:
        throw new RuntimeException("Unknown variant: " + state.getValue(VARIANT));
    }
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

    TileEntity tileEntity = worldIn.getTileEntity(pos);

    if (tileEntity != null
        && tileEntity instanceof TileEntityWorkbenchBase) {
      ((TileEntityWorkbenchBase) tileEntity).updateRecipe();
    }

    if (worldIn.isRemote) {
      return true;
    }

    playerIn.openGui(ModPWCustom.INSTANCE, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
    return true;
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

    TileEntity tileEntity = worldIn.getTileEntity(pos);

    if (tileEntity instanceof IContainer) {
      List<ItemStack> drops = ((IContainer) tileEntity).getBlockBreakDrops();

      for (ItemStack drop : drops) {
        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), drop);
      }
    }

    super.breakBlock(worldIn, pos, state);
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
  public boolean isFullCube(IBlockState state) {

    return false;
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {

    return false;
  }

  @Override
  public IProperty<EnumType> getVariant() {

    return VARIANT;
  }

  public enum EnumType
      implements IVariant {

    TAILOR(0, "tailor"),
    CARPENTER(1, "carpenter"),
    MASON(2, "mason"),
    BLACKSMITH(3, "blacksmith"),
    JEWELER(4, "jeweler");

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

    public static EnumType fromName(String name) {

      EnumType[] values = EnumType.values();

      for (EnumType value : values) {

        if (value.name.equals(name)) {
          return value;
        }
      }

      throw new IllegalArgumentException("Unknown name: " + name);
    }

    public static EnumType fromMeta(int meta) {

      if (meta < 0 || meta >= META_LOOKUP.length) {
        meta = 0;
      }

      return META_LOOKUP[meta];
    }

  }
}

























