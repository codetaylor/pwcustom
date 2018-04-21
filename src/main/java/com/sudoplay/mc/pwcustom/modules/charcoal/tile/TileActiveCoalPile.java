package com.sudoplay.mc.pwcustom.modules.charcoal.tile;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockTarCollector;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockTarDrain;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleFluids;
import com.sudoplay.mc.pwcustom.util.BlockMetaMatcher;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class TileActiveCoalPile
    extends TileActivePileBase {

  public static final List<BlockMetaMatcher> VALID_STRUCTURE_BLOCK_LIST;

  static {
    VALID_STRUCTURE_BLOCK_LIST = new ArrayList<>();
    VALID_STRUCTURE_BLOCK_LIST.add(new BlockMetaMatcher(ModuleCharcoal.Blocks.COAL_PILE_ACTIVE, 0));
    VALID_STRUCTURE_BLOCK_LIST.add(new BlockMetaMatcher(ModuleCharcoal.Blocks.COAL_PILE_ASH, 0));
    VALID_STRUCTURE_BLOCK_LIST.add(new BlockMetaMatcher(ModuleCharcoal.Blocks.REFRACTORY_BRICK, 0));
    VALID_STRUCTURE_BLOCK_LIST.add(new BlockMetaMatcher(
        ModuleCharcoal.Blocks.TAR_COLLECTOR,
        BlockTarCollector.EnumType.BRICK.getMeta()
    ));
    VALID_STRUCTURE_BLOCK_LIST.add(new BlockMetaMatcher(
        ModuleCharcoal.Blocks.TAR_DRAIN,
        BlockTarDrain.EnumType.BRICK.getMeta()
    ));
    VALID_STRUCTURE_BLOCK_LIST.add(new BlockMetaMatcher(Blocks.IRON_DOOR, OreDictionary.WILDCARD_VALUE));
  }

  @Override
  protected FluidStack getFluidProducedPerBurnStage() {

    return FluidRegistry.getFluidStack(ModuleFluids.COAL_TAR.getName(), 50);
  }

  @Override
  protected int getTotalBurnTimeTicks() {

    return 1000;
  }

  @Override
  protected void onAllBurnStagesComplete() {

    IBlockState state = ModuleCharcoal.Blocks.COAL_PILE_ASH.getDefaultState();
    this.world.setBlockState(this.pos, state);
  }

  @Override
  protected int getTotalStages() {

    return 10;
  }

  @Override
  protected boolean isValidStructureBlock(World world, BlockPos pos, IBlockState blockState, EnumFacing facing) {

    for (BlockMetaMatcher matcher : VALID_STRUCTURE_BLOCK_LIST) {

      if (matcher.test(blockState)) {
        return true;
      }
    }

    return false;
  }
}
