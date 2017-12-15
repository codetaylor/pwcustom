package com.sudoplay.mc.pwcustom.modules.mortar.tile;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.opengl.GL11;

public class TESRMortar
    extends TileEntitySpecialRenderer<TileEntityMortarBase> {

  private IBakedModel bakedModel;

  @Override
  public void render(
      TileEntityMortarBase tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha
  ) {

    GlStateManager.pushMatrix();
    GlStateManager.translate(
        (float) x - tile.getPos().getX(),
        (float) y - tile.getPos().getY(),
        (float) z - tile.getPos().getZ()
    );
    this.renderModel(tile);
    GlStateManager.popMatrix();

    GlStateManager.pushMatrix();
    GlStateManager.translate((float) x + 0.5F, (float) y, (float) z + 0.5F);

    ItemStackHandler itemStackHandler = tile.getItemStackHandler();
    double offsetY = 0.125;
    double scale = 1.0;

    if (tile.getMortarMode() == EnumMortarMode.CRUSHING) {
      scale = 3.0;
      offsetY = 0.2;
    }

    for (int i = 0; i < itemStackHandler.getSlots(); i++) {
      offsetY = this.renderItem(itemStackHandler.getStackInSlot(i), offsetY, scale);
    }

    GlStateManager.popMatrix();
  }

  private IBakedModel getBakedModel() {

    if (this.bakedModel == null) {

      IModel model;

      try {
        model = ModelLoaderRegistry.getModel(new ResourceLocation(ModPWCustom.MOD_ID, "block/mortar_wood"));

      } catch (Exception e) {
        throw new RuntimeException(e);
      }

      this.bakedModel = model.bake(
          TRSRTransformation.identity(), DefaultVertexFormats.ITEM,
          location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString())
      );
    }

    return bakedModel;
  }

  private void renderModel(TileEntityMortarBase tile) {

    GlStateManager.pushMatrix();
    RenderHelper.disableStandardItemLighting();
    this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

    if (Minecraft.isAmbientOcclusionEnabled()) {
      GlStateManager.shadeModel(GL11.GL_SMOOTH);

    } else {
      GlStateManager.shadeModel(GL11.GL_FLAT);
    }

    World world = tile.getWorld();

    Tessellator tessellator = Tessellator.getInstance();
    tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
    Minecraft.getMinecraft()
        .getBlockRendererDispatcher()
        .getBlockModelRenderer()
        .renderModel(
            world,
            this.getBakedModel(),
            world.getBlockState(tile.getPos()),
            tile.getPos(),
            Tessellator.getInstance().getBuffer(),
            false,
            0
        );
    tessellator.draw();

    RenderHelper.enableStandardItemLighting();
    GlStateManager.popMatrix();
  }

  private double renderItem(ItemStack itemStack, double offsetY, double scale) {

    if (!itemStack.isEmpty()) {

      Item item = itemStack.getItem();

      if (item instanceof ItemBlock) {
        Block block = ((ItemBlock) item).getBlock();
        int lightValue = block.getLightValue(block.getStateFromMeta(itemStack.getMetadata()));

        if (lightValue == 0) {
          RenderHelper.enableStandardItemLighting();
          GlStateManager.enableLighting();
        }
      }

      GlStateManager.pushMatrix();

      if (item instanceof ItemSkull) {
        GlStateManager.translate(0, offsetY + 0.05, 0);
        GlStateManager.scale(.2f * scale, .2f * scale, .2f * scale);
        offsetY += 0.1;

      } else if (item instanceof ItemBlock) {
        GlStateManager.translate(0, offsetY, 0);
        GlStateManager.scale(.1f * scale, .1f * scale, .1f * scale);
        //GlStateManager.rotate(90, 1, 0, 0);
        offsetY += 0.1;

      } else {
        GlStateManager.translate(0, offsetY - 0.05, 0);
        GlStateManager.scale(.2f * scale, .2f * scale, .2f * scale);
        GlStateManager.rotate(270, 1, 0, 0);
        offsetY += 0.015;
      }

      GlStateManager.pushMatrix();
      Minecraft.getMinecraft().getRenderItem().renderItem(itemStack, ItemCameraTransforms.TransformType.NONE);

      GlStateManager.popMatrix();
      GlStateManager.popMatrix();
    }

    return offsetY;
  }
}
