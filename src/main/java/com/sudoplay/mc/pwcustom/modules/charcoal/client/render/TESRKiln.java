package com.sudoplay.mc.pwcustom.modules.charcoal.client.render;

import com.sudoplay.mc.pwcustom.modules.charcoal.block.BlockKiln;
import com.sudoplay.mc.pwcustom.modules.charcoal.init.ModuleBlocks;
import com.sudoplay.mc.pwcustom.modules.charcoal.tile.TileKiln;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TESRKiln
    extends TileEntitySpecialRenderer<TileKiln> {

  @Override
  public void render(TileKiln te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

    GlStateManager.pushAttrib();
    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);
    GlStateManager.disableRescaleNormal();

    World world = te.getWorld();
    IBlockState blockState = world.getBlockState(te.getPos());

    if (blockState.getBlock() == ModuleBlocks.KILN
        && blockState.getValue(BlockKiln.VARIANT) == BlockKiln.EnumType.EMPTY) {

      ItemStack stack = te.getStackHandler().getStackInSlot(0);

      if (!stack.isEmpty()) {
        EntityItem item = te.getEntityItem();
        item.hoverStart = -2;

        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5D, -0.1D, 0.5D);
        GlStateManager.scale(1D, 1D, 1D);
        Minecraft.getMinecraft().getRenderManager().renderEntity(item, 0, 0, 0, 0, 0, false);
        GlStateManager.popMatrix();
      }
    }

    GlStateManager.popAttrib();
    GlStateManager.popMatrix();
  }
}
