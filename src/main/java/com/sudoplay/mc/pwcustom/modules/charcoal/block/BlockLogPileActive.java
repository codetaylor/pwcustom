package com.sudoplay.mc.pwcustom.modules.charcoal.block;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileActiveLogPile;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileActivePileBase;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockLogPileActive
    extends Block {

  public static final String NAME = "log_pile_active";

  public BlockLogPileActive() {

    super(Material.WOOD);
    this.setHardness(2);
    this.setSoundType(SoundType.WOOD);
    this.setHarvestLevel("axe", 0);
  }

  @Override
  public void neighborChanged(
      IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos
  ) {

    TileEntity tileEntity = worldIn.getTileEntity(pos);

    if (tileEntity instanceof TileActivePileBase) {
      ((TileActivePileBase) tileEntity).setNeedStructureValidation();
    }
  }

  @Override
  public void randomDisplayTick(
      IBlockState stateIn, World worldIn, BlockPos pos, Random rand
  ) {

    double centerX = pos.getX() + 0.5F;
    double centerY = pos.getY() + 2F;
    double centerZ = pos.getZ() + 0.5F;
    worldIn.spawnParticle(
        EnumParticleTypes.SMOKE_NORMAL,
        centerX + (rand.nextDouble() - 0.5),
        centerY,
        centerZ + (rand.nextDouble() - 0.5),
        0.0D,
        0.1D,
        0.0D
    );
    worldIn.spawnParticle(
        EnumParticleTypes.SMOKE_NORMAL,
        centerX + (rand.nextDouble() - 0.5),
        centerY,
        centerZ + (rand.nextDouble() - 0.5),
        0.0D,
        0.15D,
        0.0D
    );
    worldIn.spawnParticle(
        EnumParticleTypes.SMOKE_NORMAL,
        centerX + (rand.nextDouble() - 0.5),
        centerY - 1,
        centerZ + (rand.nextDouble() - 0.5),
        0.0D,
        0.1D,
        0.0D
    );
    worldIn.spawnParticle(
        EnumParticleTypes.SMOKE_NORMAL,
        centerX + (rand.nextDouble() - 0.5),
        centerY - 1,
        centerZ + (rand.nextDouble() - 0.5),
        0.0D,
        0.15D,
        0.0D
    );
  }

  @Override
  public boolean canSilkHarvest(
      World world, BlockPos pos, @Nonnull IBlockState state, EntityPlayer player
  ) {

    return false;
  }

  @Nonnull
  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {

    return Item.getItemFromBlock(ModuleCharcoal.Blocks.LOG_PILE);
  }

  @Override
  public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {

    return true;
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {

    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {

    return new TileActiveLogPile();
  }
}
