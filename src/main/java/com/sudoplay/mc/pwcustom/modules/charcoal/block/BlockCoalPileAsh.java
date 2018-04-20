package com.sudoplay.mc.pwcustom.modules.charcoal.block;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BlockCoalPileAsh
    extends Block {

  public static final String NAME = "coal_pile_ash";

  public static final int MIN_DROP_COUNT = 6;
  public static final int MAX_DROP_COUNT = 9;

  public BlockCoalPileAsh() {

    super(Material.GROUND);
    this.setHardness(0.6F);
    this.setHarvestLevel("shovel", 0);
    this.setSoundType(SoundType.SAND);
  }

  @Override
  public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

    List<ItemStack> result = new java.util.ArrayList<>();
    Random rand = world instanceof World ? ((World) world).rand : RANDOM;
    int count = this.quantityDropped(state, fortune, rand);

    for (int i = 0; i < count; i++) {
      Item item = this.getItemDropped(state, rand, fortune);
      result.add(new ItemStack(item, 1, this.damageDropped(state)));
    }

    for (int i = 0; i < MAX_DROP_COUNT - count; i++) {
      result.add(new ItemStack(ModuleCharcoal.Items.COAL_ASH, 1, 0));
    }

    return result;
  }

  @Nonnull
  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {

    return ModuleCharcoal.Items.COAL_COKE;
  }

  @Override
  public int damageDropped(IBlockState state) {

    return 0;
  }

  @Override
  public int quantityDropped(IBlockState state, int fortune, Random random) {

    int range = Math.max(MAX_DROP_COUNT - MIN_DROP_COUNT, 1);
    return random.nextInt(range) + Math.max(MIN_DROP_COUNT, 0) + fortune;
  }

  @Override
  public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {

    Random rand = world instanceof World ? ((World) world).rand : new Random();
    return rand.nextInt(3);
  }

  @Override
  public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {

    return false;
  }
}
