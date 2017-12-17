package com.sudoplay.mc.pwcustom.modules.mortar.event;

import com.sudoplay.mc.pwcustom.lib.util.StackUtil;
import com.sudoplay.mc.pwcustom.modules.mortar.block.BlockMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.render.HUDRender;
import com.sudoplay.mc.pwcustom.modules.mortar.tile.TileEntityMortarBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class MortarEventHandler {

  @SubscribeEvent
  public static void onLeftClickBlockEvent(PlayerInteractEvent.LeftClickBlock event) {

    World world = event.getWorld();

    if (world.isRemote) {
      return;
    }

    BlockPos pos = event.getPos();
    IBlockState blockState = world.getBlockState(pos);
    EntityPlayer entityPlayer = event.getEntityPlayer();

    if (entityPlayer.isSneaking()
        && blockState.getBlock() instanceof BlockMortar) {
      event.setUseBlock(Event.Result.DENY);
      event.setUseItem(Event.Result.DENY);
      event.setCanceled(true);

      TileEntity tileEntity = world.getTileEntity(pos);

      if (tileEntity instanceof TileEntityMortarBase) {
        ItemStack itemStack = ((TileEntityMortarBase) tileEntity).destroy(false, false, SoundEvents.ENTITY_ITEM_PICKUP);
        StackUtil.spawnStackOnTop(world, itemStack, pos);
      }
    }
  }

  @SubscribeEvent
  public static void onRenderGameOverlayPreEvent(RenderGameOverlayEvent.Pre event) {

    RenderGameOverlayEvent.ElementType type = event.getType();

    if (type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
      Minecraft minecraft = Minecraft.getMinecraft();
      RayTraceResult rayTraceResult = minecraft.objectMouseOver;

      if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) {
        return;
      }

      BlockPos blockPos = rayTraceResult.getBlockPos();

      if (blockPos.getY() < 0 || blockPos.getY() >= 256) {
        // Sanity check.
        return;
      }

      IBlockState blockState = minecraft.world.getBlockState(blockPos);
      Block block = blockState.getBlock();

      if (block instanceof BlockMortar) {
        event.setCanceled(true);
      }
    }
  }

  @SubscribeEvent
  public static void onRenderGameOverlayPostEvent(RenderGameOverlayEvent.Post event) {

    RenderGameOverlayEvent.ElementType type = event.getType();

    if (type != RenderGameOverlayEvent.ElementType.TEXT) {
      return;
    }

    HUDRender.render(event.getResolution());
  }

}
