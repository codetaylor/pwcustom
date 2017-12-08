package com.sudoplay.mc.pwcustom.workbench.tile;

import com.sudoplay.mc.pwcustom.ModPWCustom;
import com.sudoplay.mc.pwcustom.api.PWCustomAPI;
import com.sudoplay.mc.pwcustom.recipe.RegistryRecipeWorkbenchBasic;
import com.sudoplay.mc.pwcustom.workbench.block.BlockWorkbenchBasic;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class TileEntityWorkbenchMason
    extends TileEntityWorkbenchBasicBase {

  private static final int TEXT_SHADOW_COLOR = new Color(151, 151, 151).getRGB();
  private static final BlockWorkbenchBasic.EnumType TYPE = BlockWorkbenchBasic.EnumType.MASON;
  private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(
      ModPWCustom.MOD_ID,
      "textures/gui/workbench_basic_mason.png"
  );

  public TileEntityWorkbenchMason() {

    super(3, 3);
  }

  @Override
  protected int getWorkbenchGuiTextShadowColor() {

    return TEXT_SHADOW_COLOR;
  }

  @Override
  public RegistryRecipeWorkbenchBasic getRecipeRegistry() {

    return PWCustomAPI.Recipes.Workbench.REGISTRY_MAP.get(TYPE.getName());
  }

  @Override
  protected ResourceLocation getBackgroundTexture() {

    return BACKGROUND_TEXTURE;
  }
}
