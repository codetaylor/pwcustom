package com.sudoplay.mc.pwcustom.modules.mortar.block;

import com.sudoplay.mc.pwcustom.lib.spi.BlockBase;
import com.sudoplay.mc.pwcustom.lib.spi.IBlockVariant;
import com.sudoplay.mc.pwcustom.lib.spi.IVariant;
import com.sudoplay.mc.pwcustom.modules.mortar.tile.TileEntityMortarBase;
import com.sudoplay.mc.pwcustom.modules.mortar.tile.TileEntityMortarWood;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.stream.Stream;

public class BlockMortar
    extends BlockBase
    implements IBlockVariant<BlockMortar.EnumType> {

  public static final String NAME = "mortar";

  public static final IProperty<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);

  private static final AxisAlignedBB AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);

  public BlockMortar() {

    super(Material.WOOD, NAME);
    this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.WOOD));
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

    TileEntity tileEntity = worldIn.getTileEntity(pos);

    if (tileEntity instanceof TileEntityMortarBase) {
      ItemStackHandler itemStackHandler = ((TileEntityMortarBase) tileEntity).getItemStackHandler();

      for (int i = 0; i < itemStackHandler.getSlots(); i++) {
        ItemStack itemStack = ((TileEntityMortarBase) tileEntity).removeItem();

        if (itemStack.isEmpty()) {
          break;
        }

        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack);
      }
    }

    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public boolean onBlockActivated(
      World world,
      BlockPos pos,
      IBlockState state,
      EntityPlayer player,
      EnumHand hand,
      EnumFacing facing,
      float hitX,
      float hitY,
      float hitZ
  ) {

    if (hand == EnumHand.MAIN_HAND) {

      ItemStack heldItem = player.getHeldItem(hand);
      TileEntity tileEntity = world.getTileEntity(pos);

      if (tileEntity instanceof TileEntityMortarBase) {
        TileEntityMortarBase tile = (TileEntityMortarBase) tileEntity;

        if (heldItem.isEmpty()) {

          if (world.isRemote) {
            return true;
          }

          if (player.isSneaking()) {
            // pop the last item out of the tile

            if (!tile.isEmpty()) {
              ItemStack itemStack = tile.removeItem();
              EntityItem entityItem = new EntityItem(
                  world,
                  pos.getX() + 0.5,
                  pos.getY() + 1.5,
                  pos.getZ() + 0.5,
                  itemStack
              );
              entityItem.motionX = 0;
              entityItem.motionY = 0.1;
              entityItem.motionZ = 0;

              world.spawnEntity(entityItem);
            }

            return true;
          }

          // TODO: do the crafting on the server

        } else {

          // Can we insert the item?
          if (tile.canInsertItem(heldItem)) {

            if (world.isRemote) {
              return true;
            }

            // insert the item in the tile on the server
            tile.insertItem(heldItem);
            return true;
          }
        }
      }
    }

    return true;
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {

    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(
      World world, IBlockState state
  ) {

    EnumType type = state.getValue(VARIANT);

    switch (type) {
      case WOOD:
        return new TileEntityMortarWood();
      case IRON:
      case STONE:
      case DIAMOND:
        throw new NotImplementedException();
      default:
        throw new IllegalArgumentException("Unknown variant: " + type);
    }
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

    return AABB;
  }

  @Override
  public Material getMaterial(IBlockState state) {

    return state.getValue(VARIANT).material;
  }

  @Override
  public MapColor getMapColor(
      IBlockState state, IBlockAccess worldIn, BlockPos pos
  ) {

    return state.getValue(VARIANT).mapColor;
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
  public boolean isFullCube(IBlockState state) {

    return false;
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {

    return false;
  }

  @Override
  public String getName(ItemStack stack) {

    return NAME + "_" + EnumType.fromMeta(stack.getMetadata()).getName();
  }

  @Override
  public IProperty<EnumType> getVariant() {

    return VARIANT;
  }

  public enum EnumType
      implements IVariant {

    WOOD(0, "wood", Material.WOOD, Material.WOOD.getMaterialMapColor()),
    IRON(1, "iron", Material.IRON, Material.IRON.getMaterialMapColor()),
    STONE(2, "stone", Material.ROCK, Material.ROCK.getMaterialMapColor()),
    DIAMOND(3, "diamond", Material.IRON, MapColor.DIAMOND);

    private static final EnumType[] META_LOOKUP = Stream.of(EnumType.values())
        .sorted(Comparator.comparing(EnumType::getMeta))
        .toArray(EnumType[]::new);

    private final int meta;
    private final String name;
    private final Material material;
    private final MapColor mapColor;

    EnumType(int meta, String name, Material material, MapColor mapColor) {

      this.meta = meta;
      this.name = name;
      this.material = material;
      this.mapColor = mapColor;
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
