package com.sudoplay.mc.pwcustom.modules.portals.block;

import com.codetaylor.mc.athenaeum.util.PortalFramePlacer;
import com.codetaylor.mc.athenaeum.util.Properties;
import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.Reference;
import com.sudoplay.mc.pwcustom.modules.portals.ModulePortals;
import com.sudoplay.mc.pwcustom.modules.portals.world.TeleporterCustom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockPortalDarklands
    extends BlockBreakable {

  public static final String NAME = "portal_darklands";
  public static final int PORTAL_FRAME_META = BlockPortalFrame.EnumType.DARKLANDS.getMeta();
  public static BlockPortalDarklands BLOCK_PORTAL;

  protected static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
  protected static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
  protected static final AxisAlignedBB Y_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);

  public BlockPortalDarklands() {

    super(Material.PORTAL, false);
    this.setDefaultState(this.blockState.getBaseState().withProperty(Properties.PORTAL_AXIS, EnumFacing.Axis.X));
    this.setTickRandomly(true);
    this.setHardness(-1);
    this.setLightLevel(0.75f);

    this.setUnlocalizedName(NAME);
    this.setRegistryName(new ResourceLocation(ModPWCustom.MOD_ID, NAME));

    BLOCK_PORTAL = this;
  }

  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

    switch (state.getValue(Properties.PORTAL_AXIS)) {
      case X:
        return X_AABB;
      case Y:
      default:
        return Y_AABB;
      case Z:
        return Z_AABB;
    }
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {

    // TODO

    super.updateTick(worldIn, pos, state, rand);

    /*if (worldIn.provider.isSurfaceWorld() && worldIn.getGameRules()
        .getBoolean("doMobSpawning") && rand.nextInt(2000) < worldIn.getDifficulty().getDifficultyId()) {
      int i = pos.getY();
      BlockPos blockpos;

      for (blockpos = pos;
           !worldIn.getBlockState(blockpos).isTopSolid() && blockpos.getY() > 0;
           blockpos = blockpos.down()) {
        //
      }


      if (i > 0 && !worldIn.getBlockState(blockpos.up()).isNormalCube()) {
        Entity entity = ItemMonsterPlacer.spawnCreature(
            worldIn,
            EntityList.getKey(EntityPigZombie.class),
            (double) blockpos.getX() + 0.5D,
            (double) blockpos.getY() + 1.1D,
            (double) blockpos.getZ() + 0.5D
        );

        if (entity != null) {
          entity.timeUntilPortal = entity.getPortalCooldown();
        }
      }
    }*/
  }

  @Nullable
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {

    return NULL_AABB;
  }

  public static int getMetaForAxis(EnumFacing.Axis axis) {

    if (axis == EnumFacing.Axis.X) {
      return 1;
    } else {
      return axis == EnumFacing.Axis.Z ? 2 : 0;
    }
  }

  public boolean isFullCube(IBlockState state) {

    return false;
  }

  public boolean trySpawnPortal(World worldIn, BlockPos pos) {

    PortalFramePlacer portalFrameUtil = new PortalFramePlacer(
        worldIn,
        pos,
        EnumFacing.Axis.X,
        ModulePortals.Blocks.PORTAL_FRAME.getStateFromMeta(PORTAL_FRAME_META),
        this
    );

    if (portalFrameUtil.isValid() && portalFrameUtil.getPortalBlockCount() == 0) {
      portalFrameUtil.placePortalBlocks();
      return true;

    } else {
      portalFrameUtil = new PortalFramePlacer(
          worldIn,
          pos,
          EnumFacing.Axis.Z,
          ModulePortals.Blocks.PORTAL_FRAME.getStateFromMeta(PORTAL_FRAME_META),
          this
      );

      if (portalFrameUtil.isValid() && portalFrameUtil.getPortalBlockCount() == 0) {
        portalFrameUtil.placePortalBlocks();
        return true;

      } else {
        return false;
      }
    }
  }

  /**
   * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
   * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
   * block, etc.
   */
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {

    EnumFacing.Axis axis = state.getValue(Properties.PORTAL_AXIS);

    if (axis != EnumFacing.Axis.X && axis != EnumFacing.Axis.Z) {
      return;
    }

    PortalFramePlacer portalFrameUtil = new PortalFramePlacer(
        worldIn,
        pos,
        axis,
        ModulePortals.Blocks.PORTAL_FRAME.getStateFromMeta(PORTAL_FRAME_META),
        this
    );

    if (!portalFrameUtil.isValid() || portalFrameUtil.getPortalBlockCount() < portalFrameUtil.getWidth() * portalFrameUtil
        .getHeight()) {
      worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
    }
  }

  @SideOnly(Side.CLIENT)
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {

    pos = pos.offset(side);
    EnumFacing.Axis axis = null;

    if (blockState.getBlock() == this) {
      axis = blockState.getValue(Properties.PORTAL_AXIS);

      if (axis == null) {
        return false;
      }

      if (axis == EnumFacing.Axis.Z && side != EnumFacing.EAST && side != EnumFacing.WEST) {
        return false;
      }

      if (axis == EnumFacing.Axis.X && side != EnumFacing.SOUTH && side != EnumFacing.NORTH) {
        return false;
      }
    }

    boolean flag = blockAccess.getBlockState(pos.west()).getBlock() == this && blockAccess.getBlockState(pos.west(2))
        .getBlock() != this;
    boolean flag1 = blockAccess.getBlockState(pos.east()).getBlock() == this && blockAccess.getBlockState(pos.east(2))
        .getBlock() != this;
    boolean flag2 = blockAccess.getBlockState(pos.north()).getBlock() == this && blockAccess.getBlockState(pos.north(2))
        .getBlock() != this;
    boolean flag3 = blockAccess.getBlockState(pos.south()).getBlock() == this && blockAccess.getBlockState(pos.south(2))
        .getBlock() != this;
    boolean flag4 = flag || flag1 || axis == EnumFacing.Axis.X;
    boolean flag5 = flag2 || flag3 || axis == EnumFacing.Axis.Z;

    if (flag4 && side == EnumFacing.WEST) {
      return true;
    } else if (flag4 && side == EnumFacing.EAST) {
      return true;
    } else if (flag5 && side == EnumFacing.NORTH) {
      return true;
    } else {
      return flag5 && side == EnumFacing.SOUTH;
    }
  }

  /**
   * Returns the quantity of items to drop on block destruction.
   */
  public int quantityDropped(Random random) {

    return 0;
  }

  /**
   * Called When an Entity Collided with the Block
   */
  @Override
  public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {

    if (worldIn.isRemote || !entityIn.isSneaking()) {
      return;
    }

    if (!entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss()) {

      if (entityIn instanceof EntityPlayerMP) {
        EntityPlayerMP player = (EntityPlayerMP) entityIn;

        if (player.timeUntilPortal > 0) {
          player.timeUntilPortal = player.getPortalCooldown();

        } else if (player.dimension != Reference.DIMENSION_ID_DARKLANDS) {
          // player is not in the dimension

          int dimension = Reference.DIMENSION_ID_DARKLANDS;

          if (!ForgeHooks.onTravelToDimension(player, dimension)) {
            return;
          }

          player.setSneaking(false);
          player.timeUntilPortal = player.getPortalCooldown();
          player.mcServer.getPlayerList()
              .transferPlayerToDimension(
                  player,
                  dimension,
                  new TeleporterCustom(
                      player.mcServer.getWorld(dimension),
                      ModulePortals.Blocks.PORTAL_FRAME.getStateFromMeta(PORTAL_FRAME_META),
                      this.getDefaultState()
                  )
              );

        } else {

          int dimension = 0;

          if (!ForgeHooks.onTravelToDimension(player, dimension)) {
            return;
          }

          player.setSneaking(false);
          player.timeUntilPortal = player.getPortalCooldown();
          player.mcServer.getPlayerList()
              .transferPlayerToDimension(
                  player,
                  dimension,
                  new TeleporterCustom(
                      player.mcServer.getWorld(dimension),
                      ModulePortals.Blocks.PORTAL_FRAME.getStateFromMeta(PORTAL_FRAME_META),
                      this.getDefaultState()
                  )
              );
        }
      }

    }
  }

  public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {

    return ItemStack.EMPTY;
  }

  /**
   * Convert the given metadata into a BlockState for this Block
   */
  public IBlockState getStateFromMeta(int meta) {

    return this.getDefaultState()
        .withProperty(Properties.PORTAL_AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
  }

  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getBlockLayer() {

    return BlockRenderLayer.TRANSLUCENT;
  }

  /**
   * Convert the BlockState into the correct metadata value
   */
  public int getMetaFromState(IBlockState state) {

    return getMetaForAxis(state.getValue(Properties.PORTAL_AXIS));
  }

  /**
   * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
   * blockstate.
   */
  public IBlockState withRotation(IBlockState state, Rotation rot) {

    switch (rot) {
      case COUNTERCLOCKWISE_90:
      case CLOCKWISE_90:

        switch (state.getValue(Properties.PORTAL_AXIS)) {
          case X:
            return state.withProperty(Properties.PORTAL_AXIS, EnumFacing.Axis.Z);
          case Z:
            return state.withProperty(Properties.PORTAL_AXIS, EnumFacing.Axis.X);
          default:
            return state;
        }

      default:
        return state;
    }
  }

  protected BlockStateContainer createBlockState() {

    return new BlockStateContainer(this, new IProperty[]{Properties.PORTAL_AXIS});
  }

  public BlockFaceShape getBlockFaceShape(
      IBlockAccess p_193383_1_,
      IBlockState p_193383_2_,
      BlockPos p_193383_3_,
      EnumFacing p_193383_4_
  ) {

    return BlockFaceShape.UNDEFINED;
  }

}