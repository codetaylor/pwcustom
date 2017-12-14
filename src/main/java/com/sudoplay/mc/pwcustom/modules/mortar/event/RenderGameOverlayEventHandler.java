package com.sudoplay.mc.pwcustom.modules.mortar.event;

import com.sudoplay.mc.pwcustom.modules.mortar.api.MortarAPI;
import com.sudoplay.mc.pwcustom.modules.mortar.recipe.RecipeMortar;
import com.sudoplay.mc.pwcustom.modules.mortar.tile.TileEntityMortarBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class RenderGameOverlayEventHandler {

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
      }

      RecipeMortar recipe = MortarAPI.RECIPE_REGISTRY.findRecipe(itemStackList.toArray(new ItemStack[itemStackList.size()]));

      if (recipe != null) {
        int x = (int) (radius + resolution.getScaledWidth() / 2) - 8 + 64;
        int y = resolution.getScaledHeight() / 2 - 8;
        minecraft.getRenderItem().renderItemAndEffectIntoGUI(recipe.getOutput(), x, y);
      }

      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableLighting();
      GlStateManager.disableBlend();
    }
  }

}
