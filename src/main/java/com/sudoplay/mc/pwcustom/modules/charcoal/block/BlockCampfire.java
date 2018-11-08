package com.sudoplay.mc.pwcustom.modules.charcoal.block;

import com.codetaylor.mc.athenaeum.spi.IVariant;
import com.codetaylor.mc.athenaeum.util.BlockHelper;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileCampfire;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.stream.Stream;

public class BlockCampfire
    extends Block {

  public static final String NAME = "campfire";

  public static final IProperty<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);
  public static final PropertyInteger WOOD = PropertyInteger.create("wood", 0, 8);

  public static AxisAlignedBB AABB_FULL = new AxisAlignedBB(0, 0, 0, 1, 6f / 16f, 1);

  public BlockCampfire() {

    super(Material.WOOD);
  }

  @Nonnull
  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

    IBlockState actualState = this.getActualState(state, source, pos);

    if (actualState.getValue(WOOD) > 0) {
      return AABB_FULL;

    } else if (actualState.getValue(VARIANT) == EnumType.NORMAL
        || actualState.getValue(VARIANT) == EnumType.LIT) {
      return new AxisAlignedBB(4f / 16f, 0, 4f / 16f, 12f / 16f, 5f / 16f, 12f / 16f);

    }

    return super.getBoundingBox(state, source, pos);
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

    ItemStack heldItem = player.getHeldItem(hand);

    if (!heldItem.isEmpty()) {

      TileEntity tileEntity = world.getTileEntity(pos);

      if (tileEntity instanceof TileCampfire) {

        int logWood = OreDictionary.getOreID("logWood");
        int[] oreIDs = OreDictionary.getOreIDs(heldItem);

        for (int oreID : oreIDs) {

          if (oreID == logWood) {

            heldItem.setCount(heldItem.getCount() - 1);
            ItemStackHandler fuelStackHandler = ((TileCampfire) tileEntity).getFuelStackHandler();

            if (fuelStackHandler.getStackInSlot(0).isEmpty()
                || fuelStackHandler.getStackInSlot(0).getCount() < 8) {

              if (!world.isRemote) {
                fuelStackHandler.insertItem(0, new ItemStack(heldItem.getItem(), 1, heldItem.getMetadata()), false);
                world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1, 1);
                BlockHelper.notifyBlockUpdate(world, pos);
              }
              return true;
            }
          }
        }
      }

    }

    return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
  }

  @Nonnull
  @Override
  protected BlockStateContainer createBlockState() {

    return new BlockStateContainer(this, WOOD, VARIANT);
  }

  @Nonnull
  @Override
  public IBlockState getStateFromMeta(int meta) {

    return this.getDefaultState();
  }

  @Override
  public int getMetaFromState(IBlockState state) {

    return 0;
  }

  @Nonnull
  @Override
  public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos) {

    TileEntity tileEntity = world.getTileEntity(pos);

    if (tileEntity instanceof TileCampfire) {

      TileCampfire tileCampfire = (TileCampfire) tileEntity;
      int fuelRemaining = tileCampfire.getFuelRemaining();
      EnumType type = tileCampfire.getState();

      return state
          .withProperty(WOOD, fuelRemaining)
          .withProperty(VARIANT, type);
    }

    return super.getActualState(state, world, pos);
  }

  @Override
  public boolean canPlaceBlockAt(World world, @Nonnull BlockPos pos) {

    return world.isSideSolid(pos.down(), EnumFacing.UP)
        && super.canPlaceBlockAt(world, pos);
  }

  @Override
  public boolean isSideSolid(IBlockState base_state, @Nonnull IBlockAccess world, BlockPos pos, EnumFacing side) {

    return false;
  }

  @Override
  public boolean isFullBlock(IBlockState state) {

    return false;
  }

  @Override
  public boolean isFullCube(IBlockState state) {

    return this.isFullBlock(state);
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {

    return this.isFullBlock(state);
  }

  @Override
  public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {

    return this.isFullBlock(state);
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {

    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {

    return new TileCampfire();
  }

  public enum EnumType
      implements IVariant {

    NORMAL(0, "normal"),
    LIT(1, "lit"),
    ASH(2, "ash");

    private static final BlockTarDrain.EnumType[] META_LOOKUP = Stream.of(BlockTarDrain.EnumType.values())
        .sorted(Comparator.comparing(BlockTarDrain.EnumType::getMeta))
        .toArray(BlockTarDrain.EnumType[]::new);

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

    @Nonnull
    @Override
    public String getName() {

      return this.name;
    }

    public static BlockTarDrain.EnumType fromMeta(int meta) {

      if (meta < 0 || meta >= META_LOOKUP.length) {
        meta = 0;
      }

      return META_LOOKUP[meta];
    }

  }
}
