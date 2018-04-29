package com.sudoplay.mc.pwcustom.modules.charcoal.event;

import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockKiln;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleBlocks;
import com.sudoplay.mc.pwcustom.modules.charcoal.recipe.PitBurnRecipe;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileActivePile;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileKiln;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileTarCollector;
import com.sudoplay.mc.pwcustom.modules.charcoal.util.FloodFill;
import com.sudoplay.mc.pwcustom.library.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.Predicate;

@Mod.EventBusSubscriber
public class IgnitionHandler {

  public static final int BLOCK_IGNITION_LIMIT = 27;

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onRightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {

    ItemStack itemStack = event.getItemStack();
    BlockPos pos = event.getPos();
    World world = event.getWorld();

    Item item = itemStack.getItem();

    if (item == Items.FLINT_AND_STEEL) {

      if (IgnitionHandler.igniteBlocks(world, pos)) {
        world.playSound(
            null,
            pos,
            SoundEvents.ITEM_FLINTANDSTEEL_USE,
            SoundCategory.BLOCKS,
            1.0F,
            Util.RANDOM.nextFloat() * 0.4F + 0.8F
        );

        event.setUseItem(Event.Result.ALLOW);
      }
    }
  }

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
          && block == ModuleBlocks.KILN) {

        if (blockState.getValue(BlockKiln.VARIANT) == BlockKiln.EnumType.WOOD) {
          world.setBlockState(offset, blockState.withProperty(BlockKiln.VARIANT, BlockKiln.EnumType.ACTIVE));
          TileEntity tileEntity = world.getTileEntity(offset);

          if (tileEntity instanceof TileKiln) {
            ((TileKiln) tileEntity).setActive(true);
          }
        }

      } else if (facing == EnumFacing.DOWN
          && block == ModuleBlocks.TAR_COLLECTOR) {

        TileEntity tileEntity = world.getTileEntity(offset);

        if (tileEntity instanceof TileTarCollector) {
          FluidTank fluidTank = ((TileTarCollector) tileEntity).getFluidTank();

          if (fluidTank.getFluidAmount() > 0) {
            ((TileTarCollector) tileEntity).setBurning(true);
          }
        }

      } else {
        IgnitionHandler.igniteBlocks(world, offset);
      }
    }
  }

  public static boolean igniteBlocks(World world, BlockPos pos) {

    IBlockState blockState = world.getBlockState(pos);
    PitBurnRecipe recipe = PitBurnRecipe.getRecipe(blockState);
    boolean result = false;

    if (recipe != null) {

      Predicate<IBlockState> predicate = recipe.getInputMatcher();

      result = FloodFill.apply(
          world,
          pos,
          (w, p) -> predicate.test(w.getBlockState(p)),
          (w, p) -> {
            w.setBlockState(p, ModuleBlocks.ACTIVE_PILE.getDefaultState());
            TileEntity tileEntity = w.getTileEntity(p);

            if (tileEntity instanceof TileActivePile) {
              ((TileActivePile) tileEntity).setRecipe(recipe);
            }
          },
          BLOCK_IGNITION_LIMIT
      );
    }

    return result;
  }

}
