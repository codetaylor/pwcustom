package com.sudoplay.mc.pwcustom.modules.charcoal.event;

import com.sudoplay.mc.pwcustom.modules.charcoal.ModuleCharcoal;
import com.sudoplay.mc.pwcustom.modules.charcoal.util.FloodFill;
import net.minecraft.init.Blocks;
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

    if (event.isCanceled()
        || world.getBlockState(pos).getBlock() != Blocks.FIRE) {
      return;
    }

    for (EnumFacing facing : event.getNotifiedSides()) {
      BlockPos offset = pos.offset(facing);

      if (world.getBlockState(offset).getBlock() == ModuleCharcoal.Blocks.LOG_PILE) {
        IgnitionHandler.igniteLogPiles(world, offset);

      } else if (world.getBlockState(offset).getBlock() == Blocks.COAL_BLOCK) {
        IgnitionHandler.igniteCoalBlocks(world, offset);
      }
    }
  }

  private static void igniteCoalBlocks(World world, BlockPos pos) {

    FloodFill.apply(
        world,
        pos,
        (w, p) -> w.getBlockState(p).getBlock() == Blocks.COAL_BLOCK,
        (w, p) -> w.setBlockState(p, ModuleCharcoal.Blocks.COAL_PILE_ACTIVE.getDefaultState()),
        27
    );
  }

  private static void igniteLogPiles(World world, BlockPos pos) {

    FloodFill.apply(
        world,
        pos,
        (w, p) -> w.getBlockState(p).getBlock() == ModuleCharcoal.Blocks.LOG_PILE,
        (w, p) -> w.setBlockState(p, ModuleCharcoal.Blocks.LOG_PILE_ACTIVE.getDefaultState()),
        27
    );
  }

}
