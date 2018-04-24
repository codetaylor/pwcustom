package com.sudoplay.mc.pwcustom.modules.charcoal.event;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockKiln;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.BurnRecipe;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileActivePile;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileKiln;
import com.sudoplay.mc.pwcustom.modules.charcoal.util.FloodFill;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.Predicate;

@Mod.EventBusSubscriber
public class IgnitionHandler {

  public static final int BLOCK_IGNITION_LIMIT = 27;

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onNeighborNotifyEvent(BlockEvent.NeighborNotifyEvent event) {

    World world = event.getWorld();
    BlockPos pos = event.getPos();

    if (world.isRemote) {
      return;
    }

    Block fireBlock = world.getBlockState(pos).getBlock();

    if (event.isCanceled()
        || fireBlock != Blocks.FIRE) {
      return;
    }

    for (EnumFacing facing : event.getNotifiedSides()) {
      BlockPos offset = pos.offset(facing);
      IBlockState blockState = world.getBlockState(offset);
      Block block = blockState.getBlock();

      if (facing == EnumFacing.DOWN
          && block == ModuleCharcoal.Blocks.KILN) {

        if (blockState.getValue(BlockKiln.VARIANT) == BlockKiln.EnumType.WOOD) {
          world.setBlockState(offset, blockState.withProperty(BlockKiln.VARIANT, BlockKiln.EnumType.ACTIVE));
          TileEntity tileEntity = world.getTileEntity(offset);

          if (tileEntity instanceof TileKiln) {
            ((TileKiln) tileEntity).setActive(true);
          }
        }

      } else {
        IgnitionHandler.igniteBlocks(world, offset);
      }
    }
  }

  public static void igniteBlocks(World world, BlockPos pos) {

    IBlockState blockState = world.getBlockState(pos);
    BurnRecipe recipe = BurnRecipe.getRecipe(blockState);

    if (recipe != null) {

      Predicate<IBlockState> predicate = recipe.getInputMatcher();

      FloodFill.apply(
          world,
          pos,
          (w, p) -> predicate.test(w.getBlockState(p)),
          (w, p) -> {
            w.setBlockState(p, ModuleCharcoal.Blocks.ACTIVE_PILE.getDefaultState());
            TileEntity tileEntity = w.getTileEntity(p);

            if (tileEntity instanceof TileActivePile) {
              ((TileActivePile) tileEntity).setRecipe(recipe);
            }
          },
          BLOCK_IGNITION_LIMIT
      );
    }
  }

}
