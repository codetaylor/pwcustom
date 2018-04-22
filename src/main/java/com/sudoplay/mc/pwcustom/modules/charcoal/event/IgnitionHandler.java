package com.sudoplay.mc.pwcustom.modules.charcoal.event;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockKiln;
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

@Mod.EventBusSubscriber
public class IgnitionHandler {

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onNeighborNotifyEvent(BlockEvent.NeighborNotifyEvent event) {

    World world = event.getWorld();
    BlockPos pos = event.getPos();

    Block fireBlock = world.getBlockState(pos).getBlock();

    if (event.isCanceled()
        || fireBlock != Blocks.FIRE) {
      return;
    }

    for (EnumFacing facing : event.getNotifiedSides()) {
      BlockPos offset = pos.offset(facing);
      IBlockState blockState = world.getBlockState(offset);
      Block block = blockState.getBlock();

      if (block == ModuleCharcoal.Blocks.LOG_PILE) {
        IgnitionHandler.igniteLogPiles(world, offset);

      } else if (block == Blocks.COAL_BLOCK) {
        IgnitionHandler.igniteCoalBlocks(world, offset);

      } else if (facing == EnumFacing.DOWN
          && block == ModuleCharcoal.Blocks.KILN) {

        if (blockState.getValue(BlockKiln.VARIANT) == BlockKiln.EnumType.WOOD) {
          world.setBlockState(offset, blockState.withProperty(BlockKiln.VARIANT, BlockKiln.EnumType.ACTIVE));
          TileEntity tileEntity = world.getTileEntity(offset);

          if (tileEntity instanceof TileKiln) {
            ((TileKiln) tileEntity).setActive(true);
          }
        }
      }
    }
  }

  public static void igniteCoalBlocks(World world, BlockPos pos) {

    FloodFill.apply(
        world,
        pos,
        (w, p) -> w.getBlockState(p).getBlock() == Blocks.COAL_BLOCK,
        (w, p) -> w.setBlockState(p, ModuleCharcoal.Blocks.COAL_PILE_ACTIVE.getDefaultState()),
        27
    );
  }

  public static void igniteLogPiles(World world, BlockPos pos) {

    FloodFill.apply(
        world,
        pos,
        (w, p) -> w.getBlockState(p).getBlock() == ModuleCharcoal.Blocks.LOG_PILE,
        (w, p) -> w.setBlockState(p, ModuleCharcoal.Blocks.LOG_PILE_ACTIVE.getDefaultState()),
        27
    );
  }

}
