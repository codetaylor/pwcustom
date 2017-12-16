package com.sudoplay.mc.pwcustom.modules.mortar.event;

import com.sudoplay.mc.pwcustom.lib.IRecipeOutputProvider;
import com.sudoplay.mc.pwcustom.lib.util.StackUtil;
import com.sudoplay.mc.pwcustom.modules.mortar.ModuleMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.block.BlockMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.reference.EnumMortarMode;
import com.sudoplay.mc.pwcustom.modules.mortar.tile.TileEntityMortarBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

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
  public static void onRenderGameOverlayPostEvent(RenderGameOverlayEvent.Post event) {

    RenderGameOverlayEvent.ElementType type = event.getType();

    if (type != RenderGameOverlayEvent.ElementType.TEXT) {
      return;
    }

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

    TileEntity tileEntity = minecraft.world.getTileEntity(blockPos);

    if (tileEntity instanceof TileEntityMortarBase) {
      ItemStackHandler itemStackHandler = ((TileEntityMortarBase) tileEntity).getItemStackHandler();

      List<ItemStack> itemStackList = new ArrayList<>();

      for (int i = 0; i < itemStackHandler.getSlots(); i++) {
        ItemStack stackInSlot = itemStackHandler.getStackInSlot(i);

        if (stackInSlot.isEmpty()) {
          break;
        }

        itemStackList.add(stackInSlot);
      }

      float angle = (float) (Math.PI * 2 / itemStackList.size());
      double radius = 32;
      ScaledResolution resolution = event.getResolution();

      GlStateManager.enableBlend();
      GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
      GlStateManager.color(1, 1, 1, 1);
      RenderHelper.enableGUIStandardItemLighting();

      for (int i = 0; i < itemStackList.size(); i++) {
        ItemStack itemStack = itemStackList.get(i);
        int x = (int) (MathHelper.cos(angle * i) * radius + resolution.getScaledWidth() / 2) - 8;
        int y = (int) (MathHelper.sin(angle * i) * radius + resolution.getScaledHeight() / 2) - 8;
        minecraft.getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);

        if (((TileEntityMortarBase) tileEntity).getMortarMode() == EnumMortarMode.CRUSHING) {
          minecraft.getRenderItem().renderItemOverlays(minecraft.fontRenderer, itemStack, x, y);
        }
      }

      IRecipeOutputProvider recipe = ((TileEntityMortarBase) tileEntity).getRecipe();

      if (recipe != null) {
        ItemStack output = recipe.getOutput();
        int x = (int) (radius + resolution.getScaledWidth() / 2) - 8 + 64;
        int y = resolution.getScaledHeight() / 2 - 8;
        minecraft.getRenderItem().renderItemAndEffectIntoGUI(output, x, y);
      }

      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableLighting();

      {
        String mode = I18n.format(((TileEntityMortarBase) tileEntity).getMortarModeString());
        String modeWithLabel = I18n.format(ModuleMortar.Lang.MORTAR_MODE_LABEL, mode);

        int x = resolution.getScaledWidth() / 2 - minecraft.fontRenderer.getStringWidth(modeWithLabel) / 2;
        int y = resolution.getScaledHeight() / 2 + 48;
        minecraft.fontRenderer.drawStringWithShadow(modeWithLabel, x, y, 0xFFFFFF);
      }

      if (ModuleMortar.IS_DEV) {
        String durabilityString = "Durability: " + ((TileEntityMortarBase) tileEntity).getDurability()
            + "/" + ((TileEntityMortarBase) tileEntity).getMaxDurability();

        int x = resolution.getScaledWidth() / 2 - minecraft.fontRenderer.getStringWidth(durabilityString) / 2;
        int y = resolution.getScaledHeight() / 2 + 58;
        minecraft.fontRenderer.drawStringWithShadow(durabilityString, x, y, 0xFFFFFF);
      }

      GlStateManager.disableBlend();
    }
  }

}
